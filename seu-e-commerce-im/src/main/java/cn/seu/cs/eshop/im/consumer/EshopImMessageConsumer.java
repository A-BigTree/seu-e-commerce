package cn.seu.cs.eshop.im.consumer;

import cn.seu.cs.eshop.common.util.JsonUtils;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.im.controller.EshopWebSocketServer;
import cn.seu.cs.eshop.im.dao.EshopImMessageDao;
import cn.seu.cs.eshop.im.db.EshopImMessageDO;
import cs.seu.cs.eshop.common.sdk.entity.dto.EshopImMessageDTO;
import jakarta.annotation.Resource;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.IM_MESSAGE_TOPIC;
import static cn.seu.cs.eshop.common.kafka.KafkaTopicConstants.imMessageTopic;
import static cn.seu.cs.eshop.im.convert.EshopImConvert.convertToEshopImMessageDO;
import static cn.seu.cs.eshop.im.convert.EshopImConvert.convertToEshopImMessageDTO;
import static cn.seu.cs.eshop.im.enums.EshopImMessageTypeEnum.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@Component
public class EshopImMessageConsumer {
    @Resource
    private EshopImMessageDao eshopImMessageDao;

    @KafkaListener(topics = imMessageTopic)
    public void consumeEshopImMessage(ConsumerRecord<String, String> record) {
        EshopImMessageDTO message = IM_MESSAGE_TOPIC.getObject(record.value());
        int msgType = message.getMsgType();
        if (msgType == LOGIN.getType()) {  // 获取消息列表
            EshopWebSocketServer.handleLoginMessage(message.getFromUserId());
        } else if (msgType == SESSION.getType()) { // 更新已读未读信息
            List<Long> ids = JsonUtils.jsonToList(message.getContent(), Long.class);
            if (CollectionUtils.isEmpty(ids)) {
                return;
            }
            eshopImMessageDao.updateMessagesStatus(ids);
            EshopWebSocketServer.handleSessionMessage(message.getToUserId(),
                    message.getFromUserId(), message.getContent());
            EshopWebSocketServer.handleSessionMessage(message.getFromUserId(),
                    message.getToUserId(), message.getContent());
        } else if (msgType == TEXT.getType() || msgType == CARD.getType()) {
            EshopImMessageDO entity = convertToEshopImMessageDO(message);
            MysqlUtils.buildEffectEntity(entity);
            eshopImMessageDao.insert(entity);
            EshopImMessageDTO result = convertToEshopImMessageDTO(entity);
            EshopWebSocketServer.handleBusinessMessage(result.getToUserId(), result);
            EshopWebSocketServer.handleBusinessMessage(result.getFromUserId(), result);
        }
    }
}
