package cn.seu.cs.eshop.service.service.product;

import cn.seu.cs.eshop.service.sdk.product.req.GetProdPropResponse;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdPropRequest;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdPropResponse;
import cn.seu.cs.eshop.service.sdk.product.req.UpdateProdPropRequest;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
public interface ProdPropService {
    BaseResponse updateProdProp(UpdateProdPropRequest request);
    ListPageProdPropResponse listPageProdProp(ListPageProdPropRequest request);
    GetProdPropResponse getProdProp(Long id, Long shopId);
}
