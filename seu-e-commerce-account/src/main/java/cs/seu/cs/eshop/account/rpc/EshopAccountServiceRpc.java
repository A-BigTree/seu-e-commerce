package cs.seu.cs.eshop.account.rpc;

import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cs.seu.cs.eshop.account.service.EmailSendService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@DubboService
@Slf4j
public class EshopAccountServiceRpc implements EshopAccountService {
    @Resource
    EmailSendService emailSendService;

    @Override
    public String dubboTest() {
        return "Hello Word!";
    }

    @Override
    public BaseResponse sendVerifyEmail(SendVerifyEmailRequest request) {
        return emailSendService.sendVerifyEmail(request);
    }
}
