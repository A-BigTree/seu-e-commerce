package cn.seu.cs.eshop.service.sdk.product.rpc;

import cn.seu.cs.eshop.service.sdk.product.category.req.*;
import cn.seu.cs.eshop.service.sdk.product.req.*;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
public interface EshopProdCategoryService {
    BaseResponse updateProdCategory(UpdateProdCategoryRequest request);
    ListPageProdCategoryResponse listPageProdCategory(ListPageProdCategoryRequest request);
    GetProdCategoryResponse getProdCategory(Long id);
    GetAllProdCategoryResponse getAllProdCategory(Long shopId);
    BaseResponse updateProdProp(UpdateProdPropRequest request);
    ListPageProdPropResponse listPageProdProp(ListPageProdPropRequest request);
    GetProdPropResponse getProdProp(Long id, Long shopId) throws EshopException;
}
