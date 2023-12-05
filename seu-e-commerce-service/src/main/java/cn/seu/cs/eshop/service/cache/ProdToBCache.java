package cn.seu.cs.eshop.service.cache;

import cn.seu.cs.eshop.common.redis.RedisService;
import cn.seu.cs.eshop.service.dao.EshopProdDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodToBCache;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/5
 */
@Component
public class ProdToBCache {
    @Resource
    RedisService redisService;
    @Resource
    EshopProdDao eshopProdDao;

    public EshopProdDO getProd(Long id) {
        EshopProdDO result = redisService.getObjectValue(id.toString(), prodToBCache, EshopProdDO.class);
        if (result == null) {
            EshopProdDO res = eshopProdDao.selectById(id);
            if (res != null) {
                redisService.setObjectValue(id.toString(), res, prodToBCache);
            }
            return res;
        }
        return result;
    }

    public void deleteProd(Long id) {
        redisService.removeValue(id.toString(), prodToBCache);
    }
}
