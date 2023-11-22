package cn.seu.cs.eshop.common.kafka;

import cn.seu.cs.eshop.common.util.JsonUtils;
import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/22
 */
@Getter
public enum KafkaTopicConfEnum {

    ;
    private final String topic;

    private final Class<?> key;

    private final Class<?> value;

    KafkaTopicConfEnum(String topic, Class<?> key, Class<?> value) {
        this.topic = topic;
        this.key = key;
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <V> V getObject(String value) {
        return JsonUtils.jsonToObject(value, (Class<? extends V>) this.value);
    }

}
