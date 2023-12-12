package cn.seu.cs.eshop.api.controller.order;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.service.sdk.order.address.req.GetOrderLevelAreaResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.GetUserAddressInfoResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.ListUserAddressResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.UpdateUserAddressRequest;
import cn.seu.cs.eshop.service.sdk.order.rpc.EshopOrderAreaService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.CUSTOMER;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@RestController
@RequestMapping("/order/area")
public class OrderAreaAddressController {
    @DubboReference(retries = 0, timeout = 4000)
    private EshopOrderAreaService eshopOrderAreaService;

    @ApiMonitor
    @CrossOrigin
    @GetMapping("/level/get")
    public GetOrderLevelAreaResponse getOrderLevelArea(@RequestParam("levelId") Long parentId) {
        return eshopOrderAreaService.getOrderLevelArea(parentId);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/address/update")
    public BaseResponse updateAddress(@RequestBody UpdateUserAddressRequest request) {
        return eshopOrderAreaService.updateAddress(request);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @GetMapping("/address/list")
    public ListUserAddressResponse listUserAddress(@AuthorUserInfo Long userId) {
        return eshopOrderAreaService.listUserAddress(userId);
    }

    @ApiMonitor
    @CrossOrigin
    @GetMapping("/address/get")
    public GetUserAddressInfoResponse getUserAddressInfo(@RequestParam("addressId") Long addressId) {
        return eshopOrderAreaService.getUserAddressInfo(addressId);
    }
}
