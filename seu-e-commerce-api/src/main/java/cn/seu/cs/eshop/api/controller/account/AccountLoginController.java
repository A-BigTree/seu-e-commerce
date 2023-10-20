package cn.seu.cs.eshop.api.controller.account;

import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/send/email/verify")
    public BaseResponse sendEmail(SendVerifyEmailRequest request) {
        return eshopAccountService.sendVerifyEmail(request);
    }
}
