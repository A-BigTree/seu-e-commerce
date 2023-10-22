package cs.seu.cs.eshop.account.service.impl;

import cn.seu.cs.eshop.account.sdk.entity.req.RegisterUserRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.SendVerifyEmailRequest;
import cn.seu.cs.eshop.common.enums.ResponseStateEnum;
import cn.seu.cs.eshop.common.nacos.ShopConf;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.common.util.RandomGenerateUtils;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import cs.seu.cs.eshop.account.cache.EmailVerifyCache;
import cs.seu.cs.eshop.account.dao.EmailVerifyDao;
import cs.seu.cs.eshop.account.pojo.db.EmailVerifyDO;
import cs.seu.cs.eshop.account.service.EmailSendService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    EmailVerifyDao emailVerifyDao;
    @Resource
    EmailVerifyCache emailVerifyCache;
    @Resource
    JavaMailSender javaMailSender;


    @Override
    @Transactional
    public BaseResponse sendVerifyEmail(SendVerifyEmailRequest request) {
        EmailVerifyDO emailVerifyDO = emailVerifyCache.getEmailVerifyDO(request.getToEmail());
        if (emailVerifyDO != null) {
            return ResponseBuilderUtils.buildResponse(BaseResponse.class, ResponseStateEnum.OPERATION_ERROR, "验证码已发送请稍后重试");
        }
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
        EmailVerifyDO entity = MysqlUtils.buildEffectEntity(new EmailVerifyDO());
        entity.setAccount(request.getToEmail());
        entity.setVerifyCode(verifyCode);
        emailVerifyDao.insert(entity);
        emailVerifyCache.setEmailVerifyDO(entity);
        javaMailSender.send(message);
        return ResponseBuilderUtils.buildSuccessResponse(BaseResponse.class, "验证码发送成功");
    }

    @Override
    public BaseResponse registerUser(RegisterUserRequest request) {


        return null;
    }
}
