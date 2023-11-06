package cn.seu.cs.eshop.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/6
 */
@SpringBootApplication(scanBasePackages = {"cn.seu.cs.eshop.common", "cn.seu.cs.eshop.service"})
@EnableDubbo
public class EshopServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EshopServiceApplication.class, args);
    }
}
