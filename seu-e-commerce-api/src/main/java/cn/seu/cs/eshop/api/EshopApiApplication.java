package cn.seu.cs.eshop.api;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@SpringBootApplication
@EnableDubbo
public class EshopApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EshopApiApplication.class, args);
    }
}
