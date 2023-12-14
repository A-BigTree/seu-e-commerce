package cn.seu.cs.eshop.service.sdk.order.rpc;

import cn.seu.cs.eshop.service.sdk.order.address.req.GetOrderLevelAreaResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.GetUserAddressInfoResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.ListUserAddressResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.UpdateUserAddressRequest;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
public interface EshopOrderAreaService {
    GetOrderLevelAreaResponse getOrderLevelArea(Long parentId);
    GetUserAddressInfoResponse getUserAddressInfo(Long addressId);
    ListUserAddressResponse listUserAddress(Long userId);
    BaseResponse updateAddress(UpdateUserAddressRequest request);
    BaseResponse changeDefaultAddress(Long addressId, Long userId);
}