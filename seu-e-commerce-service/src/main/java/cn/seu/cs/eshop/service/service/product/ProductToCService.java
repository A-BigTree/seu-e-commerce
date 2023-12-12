package cn.seu.cs.eshop.service.service.product;

import cn.seu.cs.eshop.service.sdk.product.prod.req.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
public interface ProductToCService {
    GetEshopIndexProdsResponse getEshopIndexProds();
    ListPageProductResponse listPageStyleProduct(ListPageStyleProductRequest request);
    ListPageProductResponse listPageUserProd(ListPageUserProdRequest request);
    ListPageProductResponse listPageCategoryProd(ListPageCategoryProdRequest request);
    ListPageProductResponse listPageSearchProd(ListPageSearchProdRequest request);
    GetProdUserInfoResponse getProdUserInfo(Long userId);
    GetProductInfoResponse getProductInfo(Long prodId, Long userId);
}
