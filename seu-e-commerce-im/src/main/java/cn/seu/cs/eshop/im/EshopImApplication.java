package cn.seu.cs.eshop.im;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@SpringBootApplication(scanBasePackages = {
        "cn.seu.cs.eshop.common",
        "cn.seu.cs.eshop.im"
})
@EnableDubbo
public class EshopImApplication {
    public static void main(String[] args){
        SpringApplication.run(EshopImApplication.class, args);
    }
}
