package cn.seu.cs.eshop.api.controller.order;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.api.dto.UserBaseDTO;
import cn.seu.cs.eshop.service.sdk.order.order.req.*;
import cn.seu.cs.eshop.service.sdk.order.rpc.EshopOrderService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.BUSINESS;
import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.CUSTOMER;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@RestController
@RequestMapping("/prod/order")
public class EshopOrderProdController {
    @DubboReference(timeout = 7000, retries = 0)
    private EshopOrderService eshopOrderService;

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/init/list")
    public InitOrderListResponse initOrderList(@RequestBody InitOrderListRequest request) {
        return eshopOrderService.initOrderList(request);
    }

    @ApiMonitor(roleType = {CUSTOMER, BUSINESS})
    @CrossOrigin
    @PostMapping("/status/change")
    public BaseResponse changeOrderStatus(@RequestBody ChangeOrderStatusRequest request,
                                          @AuthorUserInfo UserBaseDTO user) {
        request.setUserId(user.getId());
        request.setModifier(user.getNickname());
        return eshopOrderService.changeOrderStatus(request);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/user/update")
    public BaseResponse updateUserOrder(@RequestBody UpdateUserOrderRequest request,
                                        @AuthorUserInfo Long userId) {
        request.getData().setUserId(userId);
        return eshopOrderService.updateUserOrder(request);
    }

    @ApiMonitor(roleType = {CUSTOMER, BUSINESS})
    @CrossOrigin
    @PostMapping("/page/list")
    public ListPageOrderResponse listPageOrder(@RequestBody ListPageOrderRequest request,
                                               @AuthorUserInfo UserBaseDTO user) {
        request.setRoleType(user.getRoleType());
        if (user.getRoleType() == BUSINESS.getValue()) {
            request.setShopId(user.getId());
        }
        if (user.getRoleType() == CUSTOMER.getValue()) {
            request.setUserId(user.getId());
        }
        return eshopOrderService.listPageOrder(request);
    }

    @ApiMonitor(roleType = {CUSTOMER, BUSINESS})
    @CrossOrigin
    @GetMapping("/review/list")
    public ListOrderReviewResponse listOrderReview(@RequestParam("id") Long orderId) {
        return eshopOrderService.listOrderReview(orderId);
    }

    @ApiMonitor(roleType = {CUSTOMER, BUSINESS})
    @CrossOrigin
    @GetMapping("/info/get")
    public GetOrderInfoResponse getOrderInfo(@RequestParam("id") Long orderId) {
        return eshopOrderService.getOrderInfo(orderId);
    }
}
