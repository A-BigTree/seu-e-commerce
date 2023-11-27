package cn.seu.cs.eshop.common.kafka.service;

import cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum;
import cn.seu.cs.eshop.common.util.JsonUtils;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
public interface EshopKafkaSendService {
    CompletableFuture<SendResult<String, String>> sendMessage(String topic, String key, String message);

    void defaultFuture(CompletableFuture<SendResult<String, String>> result);

    default void sendMessage(KafkaTopicConfEnum topic, Object message) {
        defaultFuture(sendMessage(topic.getTopic(), null, JsonUtils.objectToJson(message)));
    }

    default void sendMessage(KafkaTopicConfEnum topic, String key, Object message) {
        defaultFuture(sendMessage(topic.getTopic(), key, JsonUtils.objectToJson(message)));
    }

    default void sendMessage(KafkaTopicConfEnum topic,
                             String key,
                             Object message,
                             Consumer<SendResult<String, String>> success,
                             Function<Throwable, ? extends SendResult<String, String>> exception) {
        CompletableFuture<SendResult<String, String>> result =
                sendMessage(topic.getTopic(), key, JsonUtils.objectToJson(message));
        result.thenAccept(success);
        result.exceptionally(exception);
    }
}
