package cn.seu.cs.eshop.account.rpc;

import cn.seu.cs.eshop.account.sdk.entity.req.LoginUserRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.LoginUserResponse;
import cn.seu.cs.eshop.account.sdk.entity.req.RegisterUserRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.account.service.EmailSendService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@DubboService(timeout = 3000, retries = 0)
@Slf4j
public class EshopAccountServiceRpc implements EshopAccountService {
    @Resource
    EmailSendService emailSendServiceImpl;

    @Override
    public BaseResponse sendVerifyEmail(SendVerifyEmailRequest request) {
        return emailSendServiceImpl.sendVerifyEmail(request);
    }

    @Override
    public BaseResponse registerUser(RegisterUserRequest request) {
        return emailSendServiceImpl.registerUser(request);
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest request) {
        return emailSendServiceImpl.loginUser(request);
    }
}
