package cn.seu.cs.eshop.service.sdk.product.rpc;

import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
public interface EshopProdToBService {
    ListPageProductResponse listPageProduct(ListPageProductRequest request);
    BaseResponse updateProduct(UpdateProductRequest request);
    GetAllProdReviewResponse getAllProdReview(Long prodId);
    BaseResponse updateProdStatus(UpdateProdStatusRequest request);
}
