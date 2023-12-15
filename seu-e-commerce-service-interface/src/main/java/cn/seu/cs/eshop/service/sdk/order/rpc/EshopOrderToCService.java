package cn.seu.cs.eshop.service.sdk.order.rpc;

import cn.seu.cs.eshop.service.sdk.order.order.req.ChangeOrderStatusRequest;
import cn.seu.cs.eshop.service.sdk.order.order.req.InitOrderListRequest;
import cn.seu.cs.eshop.service.sdk.order.order.req.InitOrderListResponse;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
public interface EshopOrderToCService {
    InitOrderListResponse initOrderList(InitOrderListRequest request);
    BaseResponse changeOrderStatus(ChangeOrderStatusRequest request);
}
