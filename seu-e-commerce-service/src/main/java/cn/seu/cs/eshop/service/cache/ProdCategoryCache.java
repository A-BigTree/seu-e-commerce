package cn.seu.cs.eshop.service.cache;

import cn.seu.cs.eshop.common.redis.RedisService;
import cn.seu.cs.eshop.service.dao.ProductCategoryDao;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryDO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodCategory;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/8
 */
@Component
public class ProdCategoryCache {
    @Resource
    RedisService redisService;
    @Resource
    ProductCategoryDao productCategoryDao;

    public List<ProductCategoryDO> getCategory(Long shopId) {
        List<ProductCategoryDO> value = redisService.getListValue(shopId.toString(), prodCategory, ProductCategoryDO.class);
        if (value == null) {
            List<ProductCategoryDO> result = productCategoryDao.selectPageByShopId(shopId);
            if (!CollectionUtils.isEmpty(result)) {
                redisService.setObjectValue(shopId.toString(), result, prodCategory);
            }
            return result;
        }
        return value;
    }

    public void removeCategory(Long shopId) {
        redisService.removeValue(shopId.toString(), prodCategory);
    }

}
