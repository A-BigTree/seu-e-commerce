package cn.seu.cs.eshop.service.sdk.order.rpc;

import cn.seu.cs.eshop.service.sdk.order.order.req.*;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
public interface EshopOrderService {
    InitOrderListResponse initOrderList(InitOrderListRequest request);
    BaseResponse changeOrderStatus(ChangeOrderStatusRequest request);
    BaseResponse updateUserOrder(UpdateUserOrderRequest request);
    ListPageOrderResponse listPageOrder(ListPageOrderRequest request);
    ListOrderReviewResponse listOrderReview(Long orderId);
    GetOrderInfoResponse getOrderInfo(Long orderId);
}
