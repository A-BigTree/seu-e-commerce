package cn.seu.cs.eshop.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/6
 */
@SpringBootApplication(scanBasePackages = {
        "cn.seu.cs.eshop.common",
        "cn.seu.cs.eshop.service",
        "cn.seu.cs.eshop.task"
})
@EnableScheduling
public class EshopTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(EshopTaskApplication.class, args);
    }
}
