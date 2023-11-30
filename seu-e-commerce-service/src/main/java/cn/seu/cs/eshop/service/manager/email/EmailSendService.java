package cn.seu.cs.eshop.service.manager.email;

import cn.seu.cs.eshop.common.kafka.service.EshopKafkaSendService;
import cs.seu.cs.eshop.common.sdk.entity.dto.EmailSendDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.EMAIL_SEND_TOPIC;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/30
 */
@Service
public class EmailSendService {
    @Resource
    EshopKafkaSendService eshopKafkaSendService;

    public void sendEmail(EmailSendDTO emailSend) {
        eshopKafkaSendService.sendMessage(EMAIL_SEND_TOPIC, emailSend);
    }
}
