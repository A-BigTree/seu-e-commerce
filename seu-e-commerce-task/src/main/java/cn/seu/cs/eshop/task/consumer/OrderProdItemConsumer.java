package cn.seu.cs.eshop.task.consumer;

import cn.seu.cs.eshop.service.manager.order.EshopOrderProdManager;
import cn.seu.cs.eshop.task.annotation.KafkaConsumerMonitor;
import cs.seu.cs.eshop.common.sdk.entity.dto.OrderItemMessageDTO;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.ORDER_PROD_ITEM_TOPIC;
import static cn.seu.cs.eshop.common.kafka.KafkaTopicConstants.orderProdItemTopic;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Component
public class OrderProdItemConsumer {
    @Resource
    private EshopOrderProdManager eshopOrderProdManager;

    @KafkaConsumerMonitor
    @KafkaListener(topics = orderProdItemTopic)
    public void consumeOrderProdItem(ConsumerRecord<String, String> record) {
        OrderItemMessageDTO message = ORDER_PROD_ITEM_TOPIC.getObject(record.value());
        eshopOrderProdManager.updateProdStocksAndSoldNum(message.getProdId(), message.getProdCount());
    }
}
