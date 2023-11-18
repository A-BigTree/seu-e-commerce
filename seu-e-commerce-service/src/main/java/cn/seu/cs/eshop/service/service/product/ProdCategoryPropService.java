package cn.seu.cs.eshop.service.service.product;

import cn.seu.cs.eshop.service.sdk.product.category.req.GetProdCategoryPropResponse;
import cn.seu.cs.eshop.service.sdk.product.category.req.UpdateProdCategoryPropRequest;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/18
 */
public interface ProdCategoryPropService {
    GetProdCategoryPropResponse getProdCategoryProp(Long id, Long shopId, Integer propType);
    BaseResponse updateProdCategoryProp(UpdateProdCategoryPropRequest request);
}
