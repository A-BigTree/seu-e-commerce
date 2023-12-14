package cn.seu.cs.eshop.api.controller.product;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.service.sdk.product.basket.req.ListUserProdBasketResponse;
import cn.seu.cs.eshop.service.sdk.product.basket.req.UpdateUserProdBasketRequest;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdBasketService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.CUSTOMER;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@RestController
@RequestMapping("/product/basket")
public class ProdBasketController {
    @DubboReference(timeout = 5000, retries = 0)
    private EshopProdBasketService eshopProdBasketService;

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/user/update")
    public BaseResponse updateUserBasket(@RequestBody UpdateUserProdBasketRequest request,
                                         @AuthorUserInfo Long userId) {
        request.getData().setUserId(userId);
        return eshopProdBasketService.updateUserProdBasket(request);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @GetMapping("/user/count/get")
    public BaseResponse getUserBasketCount(@AuthorUserInfo Long userId) {
        return eshopProdBasketService.getUserProdBasketCount(userId);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @GetMapping("/user/list")
    public ListUserProdBasketResponse listBasket(@AuthorUserInfo Long userId) {
        return eshopProdBasketService.listUserProdBasket(userId);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/prod/count/change")
    public BaseResponse changeProdCount(@RequestParam("id") Long basketId, @RequestParam("count") Integer count) {
        return eshopProdBasketService.changeProdBasketCount(basketId, count);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/batch/delete")
    public BaseResponse deleteBaskets(@RequestBody List<Long> ids) {
        return eshopProdBasketService.batchDeleteBaskets(ids);
    }
}
