package cn.seu.cs.eshop.account.rpc;

import cn.seu.cs.eshop.account.sdk.entity.req.*;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.account.service.UserLoginService;
import cn.seu.cs.eshop.common.aop.RpcMonitor;
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
    UserLoginService userLoginServiceImpl;

    @Override
    @RpcMonitor
    public BaseResponse sendVerifyEmail(SendVerifyEmailRequest request) {
        return userLoginServiceImpl.sendVerifyEmail(request);
    }

    @Override
    @RpcMonitor
    public BaseResponse registerUser(RegisterUserRequest request) {
        return userLoginServiceImpl.registerUser(request);
    }

    @Override
    @RpcMonitor
    public LoginUserResponse loginUser(LoginUserRequest request) {
        return userLoginServiceImpl.loginUser(request);
    }

    @Override
    @RpcMonitor
    public GetUserInfoResponse getUserInfo(Long id) {
        return userLoginServiceImpl.getUserInfo(id);
    }
}
