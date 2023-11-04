package cn.seu.cs.eshop.api.controller.account;

import cn.seu.cs.eshop.account.sdk.entity.req.GetAccountInfoResponse;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoResponse;
import cn.seu.cs.eshop.account.sdk.entity.req.UpdateRegisterStateRequest;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.api.dto.UserBaseDTO;
import cn.seu.cs.eshop.common.enums.UserRoleEnum;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
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


    /**
     * 修改账户注册状态
     */
    @ApiMonitor(roleType = UserRoleEnum.PLATFORM)
    @CrossOrigin
    @PostMapping("/register/state/update")
    public BaseResponse updateRegisterState(@RequestBody UpdateRegisterStateRequest request,
                                            @AuthorUserInfo UserBaseDTO user) {
        System.out.println(user);
        request.setModifier(user.getNickname());
        return eshopAccountService.updateRegisterState(request);
    }

}
