package cn.seu.cs.eshop.service.rpc.order;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.order.address.req.GetOrderLevelAreaResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.GetUserAddressInfoResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.ListUserAddressResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.UpdateUserAddressRequest;
import cn.seu.cs.eshop.service.sdk.order.rpc.EshopOrderAreaService;
import cn.seu.cs.eshop.service.service.order.OrderAreaService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@DubboService
public class EshopOrderAreaServiceRpc implements EshopOrderAreaService {
    @Resource
    private OrderAreaService orderAreaService;

    @Override
    @RpcMonitor
    public GetOrderLevelAreaResponse getOrderLevelArea(Long parentId) {
        return orderAreaService.getOrderLevelArea(parentId);
    }

    @Override
    @RpcMonitor
    public GetUserAddressInfoResponse getUserAddressInfo(Long addressId) {
        return orderAreaService.getUserAddressInfo(addressId);
    }

    @Override
    @RpcMonitor
    public ListUserAddressResponse listUserAddress(Long userId) {
        return orderAreaService.listUserAddress(userId);
    }

    @Override
    @RpcMonitor
    public BaseResponse updateAddress(UpdateUserAddressRequest request) {
        return orderAreaService.updateAddress(request);
    }

    @Override
    public BaseResponse changeDefaultAddress(Long addressId, Long userId) {
        return orderAreaService.changeDefaultAddress(addressId, userId);
    }
}
