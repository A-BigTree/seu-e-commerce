package cn.seu.cs.eshop.task.consumer;

import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.dao.EshopProdUserHistoryDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdUserHistoryDO;
import cn.seu.cs.eshop.task.annotation.KafkaConsumerMonitor;
import cs.seu.cs.eshop.common.sdk.entity.dto.EshopProdUserHistoryDTO;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.PROD_USER_HISTORY_TOPIC;
import static cn.seu.cs.eshop.common.kafka.KafkaTopicConstants.prodUserHistoryTopic;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Component
public class ProdUserViewHistoryConsumer {
    @Resource
    EshopProdUserHistoryDao eshopProdUserHistoryDao;
    @KafkaConsumerMonitor
    @KafkaListener(topics = prodUserHistoryTopic)
    public void consumeViewHistory(ConsumerRecord<String, String> record) {
        EshopProdUserHistoryDTO history = PROD_USER_HISTORY_TOPIC.getObject(record.value());
        EshopProdUserHistoryDO his = eshopProdUserHistoryDao.selectByUserIdAndProdId(history.getUserId(), history.getProdId());
        if (his != null) {
            his.setCreateTime(TimeUtils.getCurrentTime());
            eshopProdUserHistoryDao.updateById(his);
        } else {
            EshopProdUserHistoryDO entity = new EshopProdUserHistoryDO();
            MysqlUtils.buildEffectEntity(entity);
            entity.setUserId(history.getUserId());
            entity.setProdId(history.getProdId());
            eshopProdUserHistoryDao.insert(entity);
        }

    }
}
