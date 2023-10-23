package cn.seu.cs.eshop.api.controller.account;

import cn.seu.cs.eshop.account.sdk.entity.dto.EshopSessionDTO;
import cn.seu.cs.eshop.account.sdk.entity.req.LoginUserRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.LoginUserResponse;
import cn.seu.cs.eshop.account.sdk.entity.req.RegisterUserRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.api.cache.UserTokenCache;
import cn.seu.cs.eshop.common.enums.ResponseStateEnum;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/20
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountLoginController {
    @DubboReference(timeout = 3000, retries = 0)
    EshopAccountService eshopAccountService;

    @Resource
    UserTokenCache userTokenCache;

    @CrossOrigin
    @PostMapping("/send/email/verify")
    public BaseResponse sendEmail(@RequestBody SendVerifyEmailRequest request) {
        return eshopAccountService.sendVerifyEmail(request);
    }

    @CrossOrigin
    @PostMapping("/user/register")
    public BaseResponse registerUser(@RequestBody RegisterUserRequest request) {
        return eshopAccountService.registerUser(request);
    }

    @CrossOrigin
    @PostMapping("/user/login")
    public LoginUserResponse loginUser(@RequestBody LoginUserRequest request) {
        LoginUserResponse response = eshopAccountService.loginUser(request);
        EshopSessionDTO session = response.getData();
        if (Objects.equals(response.getCode(), ResponseStateEnum.OK.getCode()) && session != null) {
            userTokenCache.setUserTokenInfo(session.getToken(), session.getId());
        }
        return response;
    }

    @CrossOrigin
    @PostMapping("/user/logout")
    public BaseResponse logoutUser(@RequestHeader("Authorization") String token) {
        userTokenCache.removeToken(token);
        return ResponseBuilderUtils.buildSuccessResponse(BaseResponse.class, "OK");
    }
}
