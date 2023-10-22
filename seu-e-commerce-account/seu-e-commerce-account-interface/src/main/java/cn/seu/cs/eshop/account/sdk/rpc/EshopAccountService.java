package cn.seu.cs.eshop.account.sdk.rpc;

import cn.seu.cs.eshop.account.sdk.entity.req.RegisterUserRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public interface EshopAccountService {
    String dubboTest();

    /**
     * 发送邮件
     * @param request 请求体
     * @return 响应结果
     */
    BaseResponse sendVerifyEmail(SendVerifyEmailRequest request);

    BaseResponse registerUser(RegisterUserRequest request);
}
