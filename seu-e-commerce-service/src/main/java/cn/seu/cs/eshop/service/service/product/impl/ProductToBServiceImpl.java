package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.account.sdk.entity.req.GetUserInfoResponse;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.common.component.EshopConfService;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.cache.product.ProdIndexToCCache;
import cn.seu.cs.eshop.service.pojo.bo.ProdReviewEmailBO;
import cn.seu.cs.eshop.service.cache.product.ProdToBCache;
import cn.seu.cs.eshop.service.cache.product.ProdSkusToBCache;
import cn.seu.cs.eshop.service.convert.EshopProductConvert;
import cn.seu.cs.eshop.service.dao.EshopProdDao;
import cn.seu.cs.eshop.service.dao.EshopProdReviewDao;
import cn.seu.cs.eshop.service.dao.EshopProdSkuDao;
import cn.seu.cs.eshop.service.manager.email.EmailSendService;
import cn.seu.cs.eshop.service.manager.product.EshopProdSkuManager;
import cn.seu.cs.eshop.service.nacos.ServiceNacosConfEnum;
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
import cs.seu.cs.eshop.common.sdk.entity.dto.EmailSendDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cn.seu.cs.eshop.service.convert.EshopProductConvert.covertDTO;
import static cn.seu.cs.eshop.service.convert.ProductCategoryConvert.convertDTO;
import static cn.seu.cs.eshop.service.enums.product.ProdStatusEnum.PUBLISHED;
import static cn.seu.cs.eshop.service.enums.product.ProdStatusEnum.TO_BE_REVIEWED;
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
    @Resource
    EshopConfService eshopConfService;
    @DubboReference(timeout = 4000, retries = 0)
    EshopAccountService eshopAccountService;
    @Resource
    EmailSendService emailSendService;
    @Resource
    ProdSkusToBCache prodSkusToBCache;
    @Resource
    ProdToBCache prodToBCache;
    @Resource
    ProdIndexToCCache prodIndexToCCache;

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
        prodToBCache.deleteProd(id);
        prodSkusToBCache.deleteProdSkus(id);
        prodIndexToCCache.removeProdIndex();
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
        Long prodID = request.getProdId();
        prodToBCache.deleteProd(prodID);
        prodIndexToCCache.removeProdIndex();
        EshopProdReviewDO entity = new EshopProdReviewDO();
        MysqlUtils.buildEffectEntity(entity);
        entity.setStatus(request.getStatus());
        entity.setOldStatus(origin.getStatus());
        entity.setProdId(request.getProdId());
        entity.setModifier(request.getModifier());
        entity.setRemark(request.getRemark());
        eshopProdReviewDao.insert(entity);
        if (request.getStatus() != TO_BE_REVIEWED.getStatus() && request.getStatus() != PUBLISHED.getStatus()) {
            // 发送审核邮件
            ProdReviewEmailBO review = eshopConfService.getConfigObject(ServiceNacosConfEnum.emailReviewContext, ProdReviewEmailBO.class);
            EmailSendDTO email = new EmailSendDTO();
            GetUserInfoResponse response = eshopAccountService.getUserInfo(origin.getShopId());
            String account = response.getData().getAccount();
            email.setTo(account);
            email.setSubject(review.getSubject());
            String context = "";
            context += review.getPrefix().formatted(account, response.getData().getNickname(),
                    origin.getProdName(), origin.getId());
            context += review.getText().get(request.getStatus()).formatted(request.getRemark());
            context += review.getSuffix().formatted(request.getModifier());
            email.setContext(context);
            emailSendService.sendEmail(email);
        }
        return buildSuccessResponse(BaseResponse.class, entity.getId().toString());
    }

    @Override
    public GetProductInfoResponse getProductInfo(Long prodId) {
        EshopProdDO prod = prodToBCache.getProd(prodId);
        List<EshopProdSkuDO> skus = prodSkusToBCache.getProdSkus(prodId);
        EshopProductDTO data = EshopProductConvert.covertDTO(prod, skus);
        return buildSuccessResponse(GetProductInfoResponse.class, data);
    }
}
