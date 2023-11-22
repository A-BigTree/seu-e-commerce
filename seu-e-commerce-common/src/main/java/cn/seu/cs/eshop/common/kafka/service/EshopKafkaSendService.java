package cn.seu.cs.eshop.common.kafka.service;

import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/22
 */
@Service
public class EshopKafkaSendService {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;


}
