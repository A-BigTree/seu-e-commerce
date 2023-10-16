package cs.seu.cs.eshop.account.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
@SpringBootApplication
@ComponentScan("cn.seu.cs.eshop.common.component")
@ConfigurationPropertiesScan("cn.seu.cs.eshop.common.configuration")
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
