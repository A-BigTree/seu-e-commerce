package cn.seu.cs.eshop.common.kafka;

import cn.seu.cs.eshop.common.util.JsonUtils;
import cs.seu.cs.eshop.common.sdk.entity.dto.EmailSendDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.EshopProdUserHistoryDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.MaxwellMessageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.OrderStatusChangeDTO;
import lombok.Getter;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConstants.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/22
 */
@Getter
public enum KafkaTopicConfEnum {
    // 邮件发送消息
    EMAIL_SEND_TOPIC(emailSendTopic, EmailSendDTO.class),
    // maxWell binlog监控消息
    MAXWELL_BINLOG_TOPIC(maxwellBinlogTopic, MaxwellMessageDTO.class),
    // 商品用户浏览历史消息
    PROD_USER_HISTORY_TOPIC(prodUserHistoryTopic, EshopProdUserHistoryDTO.class),
    // 订单状态变更消息
    ORDER_STATUS_CHANGE_TOPIC(orderStatusChangeTopic, OrderStatusChangeDTO.class),


    ;
    private final String topic;

    private final Class<?> value;

    KafkaTopicConfEnum(String topic, Class<?> value) {
        this.topic = topic;
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <V> V getObject(String value) {
        return JsonUtils.jsonToObject(value, (Class<? extends V>) this.value);
    }

}
