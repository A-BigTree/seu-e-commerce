package cn.seu.cs.eshop.task.consumer;

import cn.seu.cs.eshop.task.annotation.KafkaConsumerMonitor;
import cn.seu.cs.eshop.task.manager.email.EmailSendManager;
import cs.seu.cs.eshop.common.sdk.entity.dto.EmailSendDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.EMAIL_SEND_TOPIC;
import static cn.seu.cs.eshop.common.kafka.KafkaTopicConstants.emailSendTopic;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/24
 */
@Component
@Slf4j
public class EmailSendConsumer {
    @Resource
    EmailSendManager emailSendManager;
    @KafkaConsumerMonitor
    @KafkaListener(topics = emailSendTopic)
    public void consumeEmailSend(ConsumerRecord<String, String> record) {
        EmailSendDTO email = EMAIL_SEND_TOPIC.getObject(record.value());
        emailSendManager.sendEmail(email);
    }
}
