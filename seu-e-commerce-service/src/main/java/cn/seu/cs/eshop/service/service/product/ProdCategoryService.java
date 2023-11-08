package cn.seu.cs.eshop.service.service.product;

import cn.seu.cs.eshop.service.sdk.product.req.*;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
public interface ProdCategoryService {
    BaseResponse updateProdCategory(UpdateProdCategoryRequest request);
    ListPageProdCategoryResponse listPageProdCategory(ListPageProdCategoryRequest request);
    GetProdCategoryResponse getProdCategory(Long id);
    GetAllProdCategoryResponse getAllProdCategory(Long shopId);
}
