package cn.seu.cs.eshop.service.cache;

import cn.seu.cs.eshop.common.redis.RedisService;
import cn.seu.cs.eshop.service.dao.EshopProdSkuDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodSkusToBCache;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/5
 */
@Component
public class ProdSkusToBCache {
    @Resource
    RedisService redisService;
    @Resource
    EshopProdSkuDao eshopProdSkuDao;

    public List<EshopProdSkuDO> getProdSkus(Long prodId) {
        List<EshopProdSkuDO> result = redisService.getListValue(prodId.toString(), prodSkusToBCache, EshopProdSkuDO.class);
        if (CollectionUtils.isEmpty(result)) {
            List<EshopProdSkuDO> res = eshopProdSkuDao.selectByProdId(prodId);
            if (!CollectionUtils.isEmpty(res)) {
                redisService.setObjectValue(prodId.toString(), res, prodSkusToBCache);
            }
            return res;
        }
        return result;
    }

    public void deleteProdSkus(Long prodId) {
        redisService.removeValue(prodId.toString(), prodSkusToBCache);
    }
}
