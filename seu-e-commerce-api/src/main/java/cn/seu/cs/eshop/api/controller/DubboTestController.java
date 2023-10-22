package cn.seu.cs.eshop.api.controller;

import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class DubboTestController {
    @DubboReference(timeout = 3000, retries = 0)
    EshopAccountService eshopAccountService;

    @GetMapping("/hello")
    public String testDubbo() {
        return eshopAccountService.dubboTest();
    }

    @GetMapping("/email/verify")
    public BaseResponse testEmail() {
        SendVerifyEmailRequest request = new SendVerifyEmailRequest();
        request.setToEmail("shuxinwang@seu.edu.cn");
        return eshopAccountService.sendVerifyEmail(request);
    }
}
