package cn.seu.cs.eshop.account.sdk.rpc;

import cn.seu.cs.eshop.account.sdk.entity.req.*;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public interface EshopAccountService {

    /**
     * 发送邮件
     */
    BaseResponse sendVerifyEmail(SendVerifyEmailRequest request);

    BaseResponse registerUser(RegisterUserRequest request);

    LoginUserResponse loginUser(LoginUserRequest request);

    GetUserInfoResponse getUserInfo(Long id);

    BaseResponse updateUserInfo(Long id, UpdateUserInfoRequest request);

    ListRegisterInfoResponse listRegisterUserInfo(ListRegisterInfoRequest request);

    GetAccountInfoResponse getAccountInfo(Long id);
}
