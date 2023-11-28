package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.service.manager.product.EshopProdSkuManager;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProductDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cn.seu.cs.eshop.service.service.AbstractCrudService;
import cn.seu.cs.eshop.service.service.product.ProductToBService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Service
public class ProductToBServiceImpl extends AbstractCrudService<EshopProductDTO>
        implements ProductToBService {
    @Resource
    EshopProdSkuManager eshopProdSkuManager;

    @Override
    public long insert(EshopProductDTO data) {
        return 0;
    }

    @Override
    public long delete(EshopProductDTO data) {
        return 0;
    }

    @Override
    public long update(EshopProductDTO data) {
        return 0;
    }

    @Override
    public ListPageProductResponse listPageProduct(ListPageProductRequest request) {
        return null;
    }

    @Override
    public BaseResponse updateProduct(UpdateProductRequest request) {
        return null;
    }

    @Override
    public GetAllProdReviewResponse getAllProdReview(Long prodId, String modifier) {
        return null;
    }

    @Override
    public BaseResponse updateProdStatus(UpdateProdStatusRequest request) {
        return null;
    }
}
