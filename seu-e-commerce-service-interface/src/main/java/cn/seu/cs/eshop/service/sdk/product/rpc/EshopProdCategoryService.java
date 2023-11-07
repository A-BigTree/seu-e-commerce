package cn.seu.cs.eshop.service.sdk.product.rpc;

import cn.seu.cs.eshop.service.sdk.product.req.GetAllProdCategoryResponse;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdCategoryRequest;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdCategoryResponse;
import cn.seu.cs.eshop.service.sdk.product.req.UpdateProdCategoryRequest;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
public interface EshopProdCategoryService {
    BaseResponse updateProdCategory(UpdateProdCategoryRequest request);
    ListPageProdCategoryResponse listPageProdCategory(ListPageProdCategoryRequest request);
    GetAllProdCategoryResponse getAllProdCategory(Long shopId);
}
