package cn.seu.cs.eshop.task.consumer;

import cn.seu.cs.eshop.common.util.JsonUtils;
import cn.seu.cs.eshop.task.annotation.KafkaConsumerMonitor;
import cs.seu.cs.eshop.common.sdk.entity.dto.MaxwellMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConstants.maxwellBinlogTopic;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/24
 */
@Slf4j
@Component
public class MaxwellBinlogConsumer {
    @KafkaConsumerMonitor
    @KafkaListener(topics = maxwellBinlogTopic)
    public void consumeBinlog(ConsumerRecord<String, String> record) {
        MaxwellMessageDTO binlog = JsonUtils.snakeCaseJsonToObject(record.value(), MaxwellMessageDTO.class);
        log.info("{}", binlog);
    }
}
