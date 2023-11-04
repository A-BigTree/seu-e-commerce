package cn.seu.cs.eshop.api.controller.account;

import cn.seu.cs.eshop.account.sdk.entity.req.GetAccountInfoResponse;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoResponse;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.common.enums.UserRoleEnum;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@RestController
@RequestMapping("/account")
public class AccountInfoController {
    @DubboReference(timeout = 4000, retries = 0)
    EshopAccountService eshopAccountService;

    /**
     * 分页获取账户信息列表
     */
    @ApiMonitor(roleType = UserRoleEnum.PLATFORM)
    @CrossOrigin
    @PostMapping("/user/register/list")
    public ListRegisterInfoResponse listRegisterUserInfo(@RequestBody ListRegisterInfoRequest request) {
        return eshopAccountService.listRegisterUserInfo(request);
    }

    /**
     * 获取账户信息详情
     */
    @ApiMonitor(roleType = UserRoleEnum.PLATFORM)
    @CrossOrigin
    @GetMapping("/account/info/get")
    public GetAccountInfoResponse getAccountInfo(@RequestParam("id") Long id) {
        return eshopAccountService.getAccountInfo(id);
    }


    // TODO 账号审核

    // TODO 封号操作
}
