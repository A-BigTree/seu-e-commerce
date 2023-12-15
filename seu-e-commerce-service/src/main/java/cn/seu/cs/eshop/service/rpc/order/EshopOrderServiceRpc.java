package cn.seu.cs.eshop.service.rpc.order;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.order.order.req.*;
import cn.seu.cs.eshop.service.sdk.order.rpc.EshopOrderService;
import cn.seu.cs.eshop.service.service.order.OrderService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@DubboService(timeout = 10000, retries = 0)
public class EshopOrderServiceRpc implements EshopOrderService {
    @Resource
    private OrderService orderService;

    @Override
    @RpcMonitor
    public InitOrderListResponse initOrderList(InitOrderListRequest request) {
        return orderService.initOrderList(request);
    }

    @Override
    @RpcMonitor
    public BaseResponse changeOrderStatus(ChangeOrderStatusRequest request) {
        return orderService.changeOrderStatus(request);
    }

    @Override
    @RpcMonitor
    public BaseResponse updateUserOrder(UpdateUserOrderRequest request) {
        return orderService.updateUserOrder(request);
    }

    @Override
    @RpcMonitor
    public ListPageOrderResponse listPageOrder(ListPageOrderRequest request) {
        return orderService.listPageOrder(request);
    }

    @Override
    @RpcMonitor
    public ListOrderReviewResponse listOrderReview(Long orderId) {
        return orderService.listOrderReview(orderId);
    }

    @Override
    @RpcMonitor
    public GetOrderInfoResponse getOrderInfo(Long orderId) {
        return orderService.getOrderInfo(orderId);
    }
}
