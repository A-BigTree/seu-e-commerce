package cn.seu.cs.eshop.api.controller.order;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.api.dto.UserBaseDTO;
import cn.seu.cs.eshop.service.sdk.order.comm.req.*;
import cn.seu.cs.eshop.service.sdk.order.rpc.EshopProdCommService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.CUSTOMER;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/28
 */
@RestController
@RequestMapping("/prod/comm")
public class EshopProdCommController {
    @DubboReference(timeout = 5000, retries = 0)
    private EshopProdCommService prodCommService;

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/page/list")
    public ListPageProdCommResponse listPageProdComm(@RequestBody ListPageProdCommRequest request) {
        return prodCommService.listPageProdComm(request);
    }


    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @GetMapping("/user/list")
    public ListUserProdCommResponse listUserProdComm(@AuthorUserInfo Long userId) {
        return prodCommService.listUserProdComm(userId);
    }


    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @PostMapping("/user/add")
    public BaseResponse addProdComm(@RequestBody AddProdCommRequest request, @AuthorUserInfo UserBaseDTO user) {
        request.getData().setUserId(user.getId());
        request.getData().setUserName(user.getNickname());
        request.getData().setHeadPic(user.getHeadPic());
        return prodCommService.addProdComm(request);
    }

    @ApiMonitor(roleType = CUSTOMER)
    @CrossOrigin
    @GetMapping("/count/get")
    public GetProdCommCountResponse getProdCommCount(@RequestParam("id") Long prodId) {
        return prodCommService.getProdCommCount(prodId);
    }
}
