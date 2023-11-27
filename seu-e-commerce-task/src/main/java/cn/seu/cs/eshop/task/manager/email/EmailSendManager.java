package cn.seu.cs.eshop.task.manager.email;

import cs.seu.cs.eshop.common.sdk.entity.dto.EmailSendDTO;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
public class EmailSendManager implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public void sendEmail(EmailSendDTO emailSend) {
        JavaMailSender javaMailSender = applicationContext.getBean(JavaMailSender.class);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSend.getFrom());
        message.setTo(emailSend.getTo());
        message.setSubject(emailSend.getSubject());
        message.setText(emailSend.getContext());
        javaMailSender.send(message);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (EmailSendManager.applicationContext == null) {
            EmailSendManager.applicationContext = applicationContext;
        }
    }
}
