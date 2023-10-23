package cn.seu.cs.eshop.account;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
@SpringBootApplication
@EnableDubbo
@ComponentScan("cn.seu.cs.eshop.common.component")
@ComponentScan("cn.seu.cs.eshop.account")
@ConfigurationPropertiesScan("cn.seu.cs.eshop.common.configuration")
public class EshopAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(EshopAccountApplication.class, args);
    }
}
