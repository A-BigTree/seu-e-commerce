package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.convert.EshopProductConvert;
import cn.seu.cs.eshop.service.dao.EshopProdDao;
import cn.seu.cs.eshop.service.dao.EshopProdReviewDao;
import cn.seu.cs.eshop.service.dao.EshopProdSkuDao;
import cn.seu.cs.eshop.service.manager.product.EshopProdSkuManager;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdReviewDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdListDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdReviewDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProductDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cn.seu.cs.eshop.service.service.AbstractCrudService;
import cn.seu.cs.eshop.service.service.product.ProductToBService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cn.seu.cs.eshop.service.convert.EshopProductConvert.convertToEshopProdReviewDTO;
import static cn.seu.cs.eshop.service.convert.EshopProductConvert.covertDTO;
import static cn.seu.cs.eshop.service.convert.ProductCategoryConvert.convertDTO;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;


/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Service
public class ProductToBServiceImpl extends AbstractCrudService<EshopProductDTO>
        implements ProductToBService {
    @Resource
    EshopProdSkuManager eshopProdSkuManager;
    @Resource
    EshopProdSkuDao eshopProdSkuDao;
    @Resource
    EshopProdDao eshopProdDao;
    @Resource
    EshopProdReviewDao eshopProdReviewDao;

    @Override
    public long insert(EshopProductDTO data) {
        EshopProdDO eshopProdDO = EshopProductConvert.covertDO(data);
        MysqlUtils.buildEffectEntity(eshopProdDO);
        eshopProdDao.insert(eshopProdDO);
        return eshopProdDO.getId();
    }

    @Override
    public long delete(EshopProductDTO data) {
        eshopProdDao.deleteById(data.getId());
        return data.getId();
    }

    @Override
    public long update(EshopProductDTO data) {
        EshopProdDO eshopProdDO = EshopProductConvert.covertDO(data);
        eshopProdDao.updateById(eshopProdDO);
        return eshopProdDO.getId();
    }

    @Override
    public ListPageProductResponse listPageProduct(ListPageProductRequest request) {
        IPage<EshopProdDO> page = eshopProdDao.selectByConditions(request.getProdId(), request.getShopId(),
                request.getProdName(), request.getStatus(), request.getCategoryId(), request.getPage());
        EshopProdListDTO data = MysqlUtils.buildPageData(EshopProdListDTO.class, page,
                prod -> covertDTO(prod, null));
        return buildSuccessResponse(ListPageProductResponse.class, data);
    }

    @Transactional
    @Override
    public BaseResponse updateProduct(UpdateProductRequest request) {
        long id = this.crudOperation(request);
        EshopProductDTO data = request.getData();
        List<EshopProdSkuDO> skus = CollectionUtils.isEmpty(data.getSkus()) ? new ArrayList<>() :
                data.getSkus().stream().map(sku -> EshopProductConvert.covertDO(sku, id)).toList();
        List<EshopProdSkuDO> origins = eshopProdSkuDao.selectByProdId(id);
        eshopProdSkuManager.updateDiffEntities(skus, origins);
        return buildSuccessResponse(BaseResponse.class, String.valueOf(id));
    }

    @Override
    public GetAllProdReviewResponse getAllProdReview(Long prodId) {
        List<EshopProdReviewDO> review = eshopProdReviewDao.selectByProdId(prodId);
        List<EshopProdReviewDTO> data = CollectionUtils.isEmpty(review) ? new ArrayList<>() :
                review.stream().map(EshopProductConvert::convertToEshopProdReviewDTO).toList();
        return buildSuccessResponse(GetAllProdReviewResponse.class, data);
    }

    @Override
    @Transactional
    public BaseResponse updateProdStatus(UpdateProdStatusRequest request) {
        EshopProdDO origin = eshopProdDao.selectById(request.getProdId());
        EshopProdDO prod = new EshopProdDO();
        prod.setId(request.getProdId());
        prod.setStatus(request.getStatus());
        prod.setUpdateTime(TimeUtils.getCurrentTime());
        eshopProdDao.updateById(prod);
        EshopProdReviewDO entity = new EshopProdReviewDO();
        MysqlUtils.buildEffectEntity(entity);
        entity.setStatus(request.getStatus());
        entity.setOldStatus(origin.getStatus());
        entity.setProdId(request.getProdId());
        entity.setModifier(request.getModifier());
        entity.setRemark(request.getRemark());
        eshopProdReviewDao.insert(entity);
        return buildSuccessResponse(BaseResponse.class, entity.getId().toString());
    }

    @Override
    public GetProductInfoResponse getProductInfo(Long prodId) {
        EshopProdDO prod = eshopProdDao.selectById(prodId);
        List<EshopProdSkuDO> skus = eshopProdSkuDao.selectByProdId(prodId);
        EshopProductDTO data = EshopProductConvert.covertDTO(prod, skus);
        return buildSuccessResponse(GetProductInfoResponse.class, data);
    }
}
