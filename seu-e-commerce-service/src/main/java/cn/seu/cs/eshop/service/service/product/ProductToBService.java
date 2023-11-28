package cn.seu.cs.eshop.service.service.product;

import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
public interface ProductToBService {
    ListPageProductResponse listPageProduct(ListPageProductRequest request);
    BaseResponse updateProduct(UpdateProductRequest request);
    GetAllProdReviewResponse getAllProdReview(Long prodId);
    BaseResponse updateProdStatus(UpdateProdStatusRequest request);
    GetProductInfoResponse getProductInfo(Long prodId);
}
