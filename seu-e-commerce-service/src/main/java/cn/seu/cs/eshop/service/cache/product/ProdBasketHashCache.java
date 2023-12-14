package cn.seu.cs.eshop.service.cache.product;

import cn.seu.cs.eshop.common.component.EshopRedisService;
import cn.seu.cs.eshop.service.dao.EshopBasketDao;
import cn.seu.cs.eshop.service.pojo.db.EshopBasketDO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.seu.cs.eshop.service.constants.FieldConstants.PROD_BASKET_COUNT;
import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.basketListUserIdCache;
import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.basketProdSkuHashCache;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@Component
public class ProdBasketHashCache {
    @Resource
    private EshopRedisService eshopRedisService;
    @Resource
    private EshopBasketDao eshopBasketDao;

    public List<EshopBasketDO> getBasketListByUserId(Long userId) {
        List<EshopBasketDO> value =
                eshopRedisService.getListValue(userId.toString(), basketListUserIdCache, EshopBasketDO.class);
        if (CollectionUtils.isEmpty(value)) {
            List<EshopBasketDO> res = eshopBasketDao.selectByUserId(userId);
            if (CollectionUtils.isNotEmpty(res)) {
                eshopRedisService.setObjectValue(userId.toString(), res, basketListUserIdCache);
            }
            return res;
        }
        return value;
    }

    public void deleteBasketListByUserId(Long userId) {
        eshopRedisService.removeValue(userId.toString(), basketListUserIdCache);
    }

    public EshopBasketDO getBasketData(Long basketId) {
        EshopBasketDO value =
                eshopRedisService.getHashObject(basketProdSkuHashCache, EshopBasketDO.class, basketId.toString());
        if (value == null) {
            EshopBasketDO basketDO = eshopBasketDao.selectById(basketId);
            if (basketDO != null) {
                eshopRedisService.setObjectValue(basketId.toString(), basketDO, basketProdSkuHashCache);
            }
            return basketDO;
        }
        return value;
    }

    public void deleteBasketData(Long basketId) {
        eshopRedisService.removeHashValue(basketProdSkuHashCache, basketId);
    }

    public void updateBasketData(EshopBasketDO basket) {
        eshopRedisService.setHashObject(basketProdSkuHashCache, basket, basket.getId());
    }

    public boolean refreshBasketHashData(Long basketId) {
        if (!eshopRedisService.existHashKey(basketProdSkuHashCache, basketId)) {
            EshopBasketDO basketDO = eshopBasketDao.selectById(basketId);
            if (basketDO != null) {
                eshopRedisService.setHashObject(basketProdSkuHashCache, basketDO, basketId);
            } else {
                return false;
            }
        }
        return true;
    }

    public long changeBasketCount(Long basketId, Integer count) {
        if (refreshBasketHashData(basketId)) {
            Integer origin =
                    eshopRedisService.getHashField(basketProdSkuHashCache, PROD_BASKET_COUNT, Integer.class, basketId);
            if (origin == null || origin + count <= 0) {
                return -1L;
            }
            return eshopRedisService.incrementHashField(basketProdSkuHashCache, PROD_BASKET_COUNT, count, basketId);
        }
        return -1;
    }

}
