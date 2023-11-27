package cn.seu.cs.eshop.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/6
 */
@SpringBootApplication(scanBasePackages = {
        "cn.seu.cs.eshop.common.component",
        "cn.seu.cs.eshop.common.configuration",
        "cn.seu.cs.eshop.service.dao",
        "cn.seu.cs.eshop.task"
})
public class EshopTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(EshopTaskApplication.class, args);
    }
}
