package cn.seu.cs.eshop.api;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@SpringBootApplication(scanBasePackages = {
        "cn.seu.cs.eshop.common.component",
        "cn.seu.cs.eshop.common.configuration",
        "cn.seu.cs.eshop.api"
})
@EnableDubbo
public class EshopApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EshopApiApplication.class, args);
    }
}
