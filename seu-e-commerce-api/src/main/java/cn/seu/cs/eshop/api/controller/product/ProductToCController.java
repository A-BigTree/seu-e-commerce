package cn.seu.cs.eshop.api.controller.product;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdToCService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.CUSTOMER;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@RestController
@RequestMapping("/product/toc")
public class ProductToCController {
    @DubboReference(timeout = 4000, retries = 0)
    EshopProdToCService eshopProdToCService;

    @ApiMonitor(isAuthor = false)
    @CrossOrigin
    @GetMapping("/shop/index/prod/get")
    public GetEshopIndexProdsResponse getEshopIndexProds() {
        return eshopProdToCService.getEshopIndexProds();
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/style/prod/list")
    public ListPageProductResponse listPageStyleProduct(@RequestBody ListPageStyleProductRequest request) {
        return eshopProdToCService.listPageStyleProduct(request);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/user/prod/list")
    public ListPageProductResponse listPageUserProd(@RequestBody ListPageUserProdRequest request,
                                                    @AuthorUserInfo Long userId) {
        request.setUserId(userId);
        return eshopProdToCService.listPageUserProd(request);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/category/prod/list")
    public ListPageProductResponse listPageCategoryProd(@RequestBody ListPageCategoryProdRequest request) {
        return eshopProdToCService.listPageCategoryProd(request);
    }


    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/search/prod/list")
    public ListPageProductResponse listPageSearchProd(@RequestBody ListPageSearchProdRequest request,
                                                      @AuthorUserInfo Long userId) {
        request.setUserId(userId);
        return eshopProdToCService.listPageSearchProd(request);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @GetMapping("/user/prod/info/get")
    public GetProdUserInfoResponse getProdUserInfo(@AuthorUserInfo Long userId) {
        return eshopProdToCService.getProdUserInfo(userId);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @GetMapping("/prod/info/get")
    public GetProductInfoResponse getProductInfo(@RequestParam("prodId") Long prodId, @AuthorUserInfo Long userId) {
        return eshopProdToCService.getProductInfo(prodId, userId);
    }
}
