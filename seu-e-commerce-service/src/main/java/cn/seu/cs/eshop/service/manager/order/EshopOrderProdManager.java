package cn.seu.cs.eshop.service.manager.order;

import cn.seu.cs.eshop.common.kafka.service.EshopKafkaSendService;
import cn.seu.cs.eshop.service.cache.product.ProdHashCache;
import cn.seu.cs.eshop.service.dao.EshopProdDao;
import cn.seu.cs.eshop.service.dao.EshopProdSkuDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.redisson.EshopRedissonLockService;
import cs.seu.cs.eshop.common.sdk.entity.dto.OrderItemMessageDTO;
import cs.seu.cs.eshop.common.sdk.exception.EshopException;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.ORDER_PROD_ITEM_TOPIC;
import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodIdRedissonLock;
import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodSkuIdRedissonLock;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Component
public class EshopOrderProdManager {
    @Resource
    private EshopProdSkuDao eshopProdSkuDao;
    @Resource
    private EshopRedissonLockService eshopRedissonLockService;
    @Resource
    private ProdHashCache prodHashCache;
    @Resource
    private EshopKafkaSendService eshopKafkaSendService;
    @Resource
    private EshopProdDao eshopProdDao;

    public void updateProdSkuStocks(Long prodId, Long skuId, Integer count) {
        // 针对当前SKU加分布锁
        RLock lock = eshopRedissonLockService.getLock(prodSkuIdRedissonLock, prodId, skuId);
        try {
            lock.lock(prodSkuIdRedissonLock.expirationTime(), prodSkuIdRedissonLock.timeUnit());
            long skuStocks = prodHashCache.getProdSkuStocks(prodId, skuId);
            if (skuStocks == -1) {
                throw new EshopException("商品不存在");
            }
            if (count > 0 && skuStocks < count) {
                throw new EshopException("商品库存不足");
            }
            // 减库存
            EshopProdSkuDO update = new EshopProdSkuDO();
            update.setId(skuId);
            update.setStocks((int)skuStocks - count);
            eshopProdSkuDao.updateById(update);
            // 更新SKU缓存
            prodHashCache.changeProSkuStocks(prodId, skuId, -count);
            // 发送订单商品变更消息
            eshopKafkaSendService.sendMessage(ORDER_PROD_ITEM_TOPIC,
                    OrderItemMessageDTO.builder()
                            .prodId(prodId)
                            .prodCount(count)
                            .build());
        } catch (Exception e) {
            if (!(e instanceof EshopException)) {
                prodHashCache.deleteProdSkuData(prodId, skuId);
            }
            throw e;
        } finally {
            lock.unlock();
        }
    }

    public void updateProdStocksAndSoldNum(Long prodId, Integer count) {
        RLock lock = eshopRedissonLockService.getLock(prodIdRedissonLock, prodId);
        try {
            lock.lock(prodIdRedissonLock.expirationTime(), prodIdRedissonLock.timeUnit());
            long prodStocks = prodHashCache.getProdTotalStocks(prodId);
            long soldNum = prodHashCache.getProdSoldNum(prodId);
            if (prodStocks == -1 || prodStocks < count) {
                prodHashCache.deleteProdData(prodId);
            }
            // 减库存加销量
            EshopProdDO update = new EshopProdDO();
            update.setId(prodId);
            update.setTotalStocks((int)prodStocks - count);
            update.setSoldNum((int)soldNum + count);
            eshopProdDao.updateById(update);
            // 更新商品缓存
            prodHashCache.changeProdTotalStocks(prodId, -count);
            prodHashCache.changeProdSoldNum(prodId, count);
        } finally {
            lock.unlock();
        }
    }
}
