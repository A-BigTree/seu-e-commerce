package cn.seu.cs.eshop.account;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
@SpringBootApplication(scanBasePackages = {"cn.seu.cs.eshop.common","cn.seu.cs.eshop.account"})
@EnableDubbo
public class EshopAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(EshopAccountApplication.class, args);
    }
}
