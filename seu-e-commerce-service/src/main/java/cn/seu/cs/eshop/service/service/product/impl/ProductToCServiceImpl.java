package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.common.component.EshopConfService;
import cn.seu.cs.eshop.common.kafka.service.EshopKafkaSendService;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.cache.product.ProdIndexToCCache;
import cn.seu.cs.eshop.service.cache.product.ProdSkusToBCache;
import cn.seu.cs.eshop.service.cache.product.ProdToBCache;
import cn.seu.cs.eshop.service.convert.EshopProductConvert;
import cn.seu.cs.eshop.service.dao.EshopProdDao;
import cn.seu.cs.eshop.service.dao.EshopProdFavoriteDao;
import cn.seu.cs.eshop.service.dao.EshopProdUserHistoryDao;
import cn.seu.cs.eshop.service.enums.product.ProdSearchTypeEnum;
import cn.seu.cs.eshop.service.es.EsProductSearchService;
import cn.seu.cs.eshop.service.pojo.bo.EshopIndexProdConfBO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdFavoriteDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdUserHistoryDO;
import cn.seu.cs.eshop.service.pojo.es.ProductEsIndex;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopIndexProdListDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdListDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdUserInfoDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProductDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cn.seu.cs.eshop.service.service.product.ProductToCService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.EshopProdUserHistoryDTO;
import cs.seu.cs.eshop.common.sdk.exception.EshopException;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;
import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.PROD_USER_HISTORY_TOPIC;
import static cn.seu.cs.eshop.service.convert.EshopProductConvert.covertDTO;
import static cn.seu.cs.eshop.service.enums.product.ProdStatusEnum.PUBLISHED;
import static cn.seu.cs.eshop.service.enums.product.ProdUserInfoTypeEnum.FAVORITE_PROD;
import static cn.seu.cs.eshop.service.enums.product.ProdUserInfoTypeEnum.HISTORY_PROD;
import static cn.seu.cs.eshop.service.nacos.ServiceNacosConfEnum.eshopIndexProdConf;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@Service
public class ProductToCServiceImpl implements ProductToCService {
    @Resource
    ProdIndexToCCache prodIndexToCCache;
    @Resource
    EshopConfService eshopConfService;
    @Resource
    EsProductSearchService esProductSearchService;
    @Resource
    EshopProdFavoriteDao eshopProdFavoriteDao;
    @Resource
    EshopProdUserHistoryDao eshopProdUserHistoryDao;
    @Resource
    EshopProdDao eshopProdDao;
    @Resource
    EshopKafkaSendService eshopKafkaSendService;
    @Resource
    ProdToBCache prodToBCache;
    @Resource
    ProdSkusToBCache prodSkusToBCache;

    @Override
    public GetEshopIndexProdsResponse getEshopIndexProds() {
        List<EshopIndexProdListDTO> data = prodIndexToCCache.getProdIndex();
        return buildSuccessResponse(GetEshopIndexProdsResponse.class, data);
    }

    @Override
    public ListPageProductResponse listPageStyleProduct(ListPageStyleProductRequest request) {
        List<EshopIndexProdConfBO> configs =
                eshopConfService.getConfigList(eshopIndexProdConf, EshopIndexProdConfBO.class);
        EshopIndexProdConfBO config = configs.stream()
                .filter(c -> c.getStyle().equals(request.getStyle()))
                .findAny()
                .orElse(null);
        if (config == null) {
            throw new EshopException("请求参数有误");
        }
        List<ProductEsIndex> indices =
                esProductSearchService.searchByConditions(StringUtils.EMPTY,
                        config.getOrderBy(), config.getOrder(), request.getPage());
        List<EshopProductDTO> prods = indices.stream()
                .map(EshopProductConvert::convertToEshopProductDTO)
                .toList();
        EshopProdListDTO data = EshopProdListDTO.builder()
                .page(request.getPage())
                .records(prods)
                .build();
        return buildSuccessResponse(ListPageProductResponse.class, data);
    }

    @Override
    public ListPageProductResponse listPageUserProd(ListPageUserProdRequest request) {
        List<Long> prodIds;
        if (request.getType() == FAVORITE_PROD.getType()) {
            IPage<EshopProdFavoriteDO> favorites =
                    eshopProdFavoriteDao.selectPageByUserId(request.getUserId(), request.getPage());
            prodIds = favorites.getRecords().stream()
                    .map(EshopProdFavoriteDO::getProdId)
                    .toList();
        } else if (request.getType() == HISTORY_PROD.getType()) {
            IPage<EshopProdUserHistoryDO> histories =
                    eshopProdUserHistoryDao.selectByUserId(request.getUserId(), request.getPage());
            prodIds = histories.getRecords().stream()
                    .map(EshopProdUserHistoryDO::getProdId)
                    .toList();
        } else {
            throw new EshopException("请求参数有误");
        }
        List<EshopProductDTO> prods = new ArrayList<>();
        if (!CollectionUtils.isEmpty(prodIds)) {
            List<EshopProdDO> prodDOS = eshopProdDao.selectByProdIds(prodIds);
            prods = prodDOS.stream()
                    .map(prod -> EshopProductConvert.covertDTO(prod, null))
                    .toList();
        }
        EshopProdListDTO data = EshopProdListDTO.builder()
                .page(request.getPage())
                .records(prods)
                .build();
        return buildSuccessResponse(ListPageProductResponse.class, data);
    }

    @Override
    public ListPageProductResponse listPageCategoryProd(ListPageCategoryProdRequest request) {
        IPage<EshopProdDO> records = eshopProdDao.selectByConditions(OFFICIAL_ID, OFFICIAL_ID,
                StringUtils.EMPTY, PUBLISHED.getStatus(), request.getCategoryId(), request.getPage());
        EshopProdListDTO data = MysqlUtils.buildPageData(EshopProdListDTO.class, records,
                prod -> covertDTO(prod, null));
        return buildSuccessResponse(ListPageProductResponse.class, data);
    }

    @Override
    public ListPageProductResponse listPageSearchProd(ListPageSearchProdRequest request) {
        if (StringUtils.isEmpty(request.getKeyword())) {
            throw new EshopException("搜索关键字不能为空");
        }
        ProdSearchTypeEnum type = ProdSearchTypeEnum.getSearchType(request.getType());
        List<ProductEsIndex> indices =
                esProductSearchService.searchByConditions(request.getKeyword(),
                        type.getOrderBy(), type.getOrder(), request.getPage());
        List<EshopProductDTO> prods = indices.stream()
                .map(EshopProductConvert::convertToEshopProductDTO)
                .toList();
        EshopProdListDTO data = EshopProdListDTO.builder()
                .page(request.getPage())
                .records(prods)
                .build();
        return buildSuccessResponse(ListPageProductResponse.class, data);
    }

    @Override
    public GetProdUserInfoResponse getProdUserInfo(Long userId) {
        long favoriteCount = eshopProdFavoriteDao.countByUserId(userId);
        long historyCount = eshopProdUserHistoryDao.countByUserId(userId);
        EshopProdUserInfoDTO data = EshopProdUserInfoDTO.builder()
                .favoriteCount(favoriteCount)
                .historyCount(historyCount)
                // TODO: 分类订单数量
                .orderCount(0L)
                .build();
        return buildSuccessResponse(GetProdUserInfoResponse.class, data);
    }

    @Override
    public GetProductInfoResponse getProductInfo(Long prodId, Long userId) {
        EshopProdDO prod = prodToBCache.getProd(prodId);
        if (prod.getStatus() != PUBLISHED.getStatus()) {
            throw new EshopException("商品已下架");
        }
        List<EshopProdSkuDO> skus = prodSkusToBCache.getProdSkus(prodId);
        EshopProductDTO data = EshopProductConvert.covertDTO(prod, skus);
        eshopKafkaSendService.sendMessage(PROD_USER_HISTORY_TOPIC,
                EshopProdUserHistoryDTO.builder()
                .userId(userId)
                .prodId(prodId)
                .build());
        return buildSuccessResponse(GetProductInfoResponse.class, data);
    }
}
