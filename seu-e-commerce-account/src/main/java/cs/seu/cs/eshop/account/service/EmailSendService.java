package cs.seu.cs.eshop.account.service;


import cn.seu.cs.eshop.account.sdk.entity.req.RegisterUserRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */

public interface EmailSendService {
    BaseResponse sendVerifyEmail(SendVerifyEmailRequest request);

    BaseResponse registerUser(RegisterUserRequest request);
}
