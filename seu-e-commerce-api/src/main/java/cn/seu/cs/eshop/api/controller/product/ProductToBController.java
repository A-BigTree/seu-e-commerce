package cn.seu.cs.eshop.api.controller.product;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.api.dto.UserBaseDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdToBService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static cn.seu.cs.eshop.api.util.ApiUtil.getShopId;
import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.BUSINESS;
import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.PLATFORM;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@RestController
@RequestMapping("/product/tob")
public class ProductToBController {
    @DubboReference(timeout = 4000, retries = 0)
    private EshopProdToBService eshopProdToBService;

    @ApiMonitor(roleType = {BUSINESS, PLATFORM})
    @CrossOrigin
    @PostMapping("/prod/page/list")
    ListPageProductResponse listPageProduct(@RequestBody ListPageProductRequest request,
                                            @AuthorUserInfo UserBaseDTO user) {
        request.setShopId(getShopId(user));
        return eshopProdToBService.listPageProduct(request);
    }

    @ApiMonitor(roleType = {BUSINESS, PLATFORM})
    @CrossOrigin
    @PostMapping("/prod/update")
    BaseResponse updateProduct(@RequestBody UpdateProductRequest request,
                               @AuthorUserInfo UserBaseDTO user) {
        request.getData().setShopId(getShopId(user));
        return eshopProdToBService.updateProduct(request);
    }

    @ApiMonitor(roleType = {BUSINESS, PLATFORM})
    @CrossOrigin
    @GetMapping("/prod/review/get")
    GetAllProdReviewResponse getAllProdReview(@RequestParam("prodId") Long prodId) {
        return eshopProdToBService.getAllProdReview(prodId);
    }

    @ApiMonitor(roleType = {BUSINESS, PLATFORM})
    @CrossOrigin
    @PostMapping("/prod/status/update")
    BaseResponse updateProdStatus(@RequestBody UpdateProdStatusRequest request,
                                  @AuthorUserInfo UserBaseDTO user) {
        request.setModifier(user.getNickname());
        return eshopProdToBService.updateProdStatus(request);
    }

    @ApiMonitor(roleType = {BUSINESS, PLATFORM})
    @CrossOrigin
    @GetMapping("/prod/info/get")
    GetProductInfoResponse getProductInfo(@RequestParam("id") Long id) {
        return eshopProdToBService.getProductInfo(id);
    }

}
