package cn.seu.cs.eshop.task.schedule;

import cn.seu.cs.eshop.common.component.EshopConfService;
import cn.seu.cs.eshop.common.kafka.service.EshopKafkaSendService;
import cn.seu.cs.eshop.service.dao.EshopOrderDao;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderDO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.ORDER_UNPAID_TIMEOUT_TOPIC;
import static cn.seu.cs.eshop.task.nacos.TaskNacosConfEnum.unpaidOrderTimeout;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Component
@Slf4j
public class EshopOrderScheduleTask {
    @Resource
    private EshopOrderDao eshopOrderDao;
    @Resource
    private EshopConfService eshopConfService;
    @Resource
    private EshopKafkaSendService eshopKafkaSendService;

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void checkOrderStatus() {
        log.info("Check order status start");
        Long timeout = eshopConfService.getConfigObject(unpaidOrderTimeout, Long.class);
        List<EshopOrderDO> orders = eshopOrderDao.selectUnpaidTimeoutOrder(timeout);
        if (CollectionUtils.isNotEmpty(orders)) {
            orders.forEach(order -> {
                eshopKafkaSendService.sendMessage(ORDER_UNPAID_TIMEOUT_TOPIC, order.getId());
            });
        }
        log.info("Check order status end");
    }
}
