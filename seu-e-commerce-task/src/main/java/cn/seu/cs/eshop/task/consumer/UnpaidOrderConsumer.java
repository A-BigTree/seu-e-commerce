package cn.seu.cs.eshop.task.consumer;

import cn.seu.cs.eshop.service.sdk.order.order.req.ChangeOrderStatusRequest;
import cn.seu.cs.eshop.service.service.order.OrderService;
import cn.seu.cs.eshop.task.annotation.KafkaConsumerMonitor;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.ORDER_UNPAID_TIMEOUT_TOPIC;
import static cn.seu.cs.eshop.common.kafka.KafkaTopicConstants.orderUnpaidTimeoutTopic;
import static cn.seu.cs.eshop.service.enums.order.EshopOrderCloseTypeEnum.TIMEOUT_CANCEL;
import static cn.seu.cs.eshop.service.enums.order.EshopOrderStatusEnum.CANCELED;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Component
public class UnpaidOrderConsumer {
    @Resource
    private OrderService orderService;

    @KafkaConsumerMonitor
    @KafkaListener(topics = orderUnpaidTimeoutTopic)
    public void consumeUnpaidOrder(ConsumerRecord<String, String> record) {
        Long orderId = ORDER_UNPAID_TIMEOUT_TOPIC.getObject(record.value());
        ChangeOrderStatusRequest request = ChangeOrderStatusRequest.builder()
                .orderId(orderId)
                .modifier(StringUtils.EMPTY)
                .status(CANCELED.getStatus())
                .param1(String.valueOf(TIMEOUT_CANCEL.getCloseType()))
                .build();
        orderService.changeOrderStatus(request);
    }
}
