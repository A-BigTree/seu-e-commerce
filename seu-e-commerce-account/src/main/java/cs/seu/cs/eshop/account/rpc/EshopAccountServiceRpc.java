package cs.seu.cs.eshop.account.rpc;

import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@DubboService
public class EshopAccountServiceRpc implements EshopAccountService {
    @Override
    public String dubboTest() {
        return "Hello Word!";
    }
}
