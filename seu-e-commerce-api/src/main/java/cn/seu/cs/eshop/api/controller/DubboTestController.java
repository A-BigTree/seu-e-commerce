package cn.seu.cs.eshop.api.controller;

import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
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
    @DubboReference
    EshopAccountService eshopAccountService;

    @GetMapping("/hello")
    public String testDubbo() {
        return eshopAccountService.dubboTest();
    }
}
