package cn.seu.cs.eshop.common.kafka.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/22
 */
@Service
@Slf4j
public class EshopKafkaSendServiceImpl implements EshopKafkaSendService {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public CompletableFuture<SendResult<String, String>> sendMessage(String topic, String key, String message) {
        return StringUtils.isEmpty(key) ?
                kafkaTemplate.send(topic, message) :
                kafkaTemplate.send(topic, key, message);
    }

    @Override
    public void defaultFuture(CompletableFuture<SendResult<String, String>> result) {
        result.thenAccept(res -> log.info("Send successfully: {}", res.getProducerRecord()));
        result.exceptionally(e -> {
            log.error("Send error, e:", e);
            return null;
        });
    }
}
