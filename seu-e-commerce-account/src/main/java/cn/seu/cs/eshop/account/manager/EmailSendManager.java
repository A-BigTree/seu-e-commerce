package cn.seu.cs.eshop.account.manager;

import cn.seu.cs.eshop.common.kafka.service.EshopKafkaSendService;
import cs.seu.cs.eshop.common.sdk.entity.dto.EmailSendDTO;
import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.EMAIL_SEND_TOPIC;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@Component
public class EmailSendManager {
    @Resource
    EshopKafkaSendService eshopKafkaSendService;

    public void sendEmail(EmailSendDTO emailSend) {
        eshopKafkaSendService.sendMessage(EMAIL_SEND_TOPIC, emailSend);
    }

    public void sendEmail(String from, String to, String subject, String context) {
        EmailSendDTO emailSend = new EmailSendDTO(from, to, subject, context);
        sendEmail(emailSend);
    }
}
