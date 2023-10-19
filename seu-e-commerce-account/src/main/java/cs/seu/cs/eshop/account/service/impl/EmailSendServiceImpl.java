package cs.seu.cs.eshop.account.service.impl;

import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cn.seu.cs.eshop.common.nacos.ShopConf;
import cn.seu.cs.eshop.common.util.RandomGenerateUtils;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import cs.seu.cs.eshop.account.manager.EmailSendManager;
import cs.seu.cs.eshop.account.service.EmailSendService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import static cs.seu.cs.eshop.account.nacos.AccountNacosConfEnum.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Service
public class EmailSendServiceImpl implements EmailSendService {
    @Resource
    ShopConf eshopConfService;
    @Resource
    EmailSendManager emailSendManager;


    @Override
    public BaseResponse sendVerifyEmail(SendVerifyEmailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        String from = request.getFromEmail();
        if (StringUtils.isEmpty(from)) {
            from = eshopConfService.getConfig(fromEmail);
        }
        message.setFrom(from);
        message.setTo(request.getToEmail());
        int length = eshopConfService.getConfigObject(emailVerifyLength, Integer.class);
        String symbols = eshopConfService.getConfig(emailVerifySymbols);
        String verifyCode = RandomGenerateUtils.generateVerCode(symbols, length);
        String context = request.getContext();
        if (StringUtils.isEmpty(context)) {
            String format = eshopConfService.getConfig(emailContext);
            context = String.format(format, request.getToEmail(), verifyCode,
                    eshopConfService.getConfigObject(emailVerifyTime, Long.class).toString());
        }
        message.setText(context);
        emailSendManager.addVerifyCode(request.getToEmail(), verifyCode, message);
        return ResponseBuilderUtils.buildSuccessResponse(BaseResponse.class);
    }


}
