package cn.seu.cs.eshop.api.controller.product;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.api.dto.UserBaseDTO;
import cn.seu.cs.eshop.service.sdk.product.req.GetAllProdCategoryResponse;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdCategoryRequest;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdCategoryResponse;
import cn.seu.cs.eshop.service.sdk.product.req.UpdateProdCategoryRequest;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdCategoryService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;
import static cn.seu.cs.eshop.common.enums.UserRoleEnum.BUSINESS;
import static cn.seu.cs.eshop.common.enums.UserRoleEnum.PLATFORM;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@RestController
@RequestMapping("/product/category")
public class ProdCategoryController {
    @DubboReference
    EshopProdCategoryService eshopProdCategoryService;

    private Long getShopId(UserBaseDTO user) {
        if (user.getRoleType() == BUSINESS.getValue()) {
            return user.getId();
        }
        return OFFICIAL_ID;
    }

    @ApiMonitor(roleType = {BUSINESS, PLATFORM})
    @CrossOrigin
    @PostMapping("/update")
    public BaseResponse updateProdCategory(@RequestBody UpdateProdCategoryRequest request) {
        return eshopProdCategoryService.updateProdCategory(request);
    }

    @ApiMonitor(roleType = {BUSINESS, PLATFORM})
    @CrossOrigin
    @PostMapping("/page/list")
    public ListPageProdCategoryResponse listPageProdCategory(@RequestBody ListPageProdCategoryRequest request,
                                                             @AuthorUserInfo UserBaseDTO user) {
        request.setShopId(getShopId(user));
        return eshopProdCategoryService.listPageProdCategory(request);
    }

    @ApiMonitor
    @CrossOrigin
    @GetMapping("/all/get")
    public GetAllProdCategoryResponse getAllProdCategory(@AuthorUserInfo UserBaseDTO user) {
        return eshopProdCategoryService.getAllProdCategory(getShopId(user));
    }
}
