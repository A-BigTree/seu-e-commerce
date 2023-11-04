package cn.seu.cs.eshop.account.manager;

import cs.seu.cs.eshop.common.sdk.entity.dto.EmailSendDTO;
import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@Component
public class EmailSendManager {
    @Resource
    JavaMailSender javaMailSender;

    public void sendEmail(EmailSendDTO emailSend) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSend.getFrom());
        message.setTo(emailSend.getTo());
        message.setSubject(emailSend.getSubject());
        message.setText(emailSend.getContext());
        javaMailSender.send(message);
    }

    public void sendEmail(String from, String to, String subject, String context) {
        EmailSendDTO emailSend = new EmailSendDTO(from, to, subject, context);
        sendEmail(emailSend);
    }
}
