package cs.seu.cs.eshop.account.rpc;

import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.core.env.Environment;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@DubboService
@Slf4j
public class EshopAccountServiceRpc implements EshopAccountService {
    @Resource
    Environment environment;

    @Override
    public String dubboTest() {
        log.info("Service from port: {}", environment.getProperty("server.port"));
        return "Hello Word!";
    }
}
