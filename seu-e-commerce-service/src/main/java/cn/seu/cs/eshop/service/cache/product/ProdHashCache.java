package cn.seu.cs.eshop.service.cache.product;

import cn.seu.cs.eshop.common.component.EshopRedisService;
import cn.seu.cs.eshop.service.dao.EshopProdDao;
import cn.seu.cs.eshop.service.dao.EshopProdSkuDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.service.constants.FieldConstants.*;
import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodHashCache;
import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodSkuHashCache;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@Component
public class ProdHashCache {
    @Resource
    private EshopRedisService eshopRedisService;
    @Resource
    EshopProdDao eshopProdDao;
    @Resource
    EshopProdSkuDao eshopProdSkuDao;

    public EshopProdDO getProdData(Long prodId) {
        EshopProdDO value = eshopRedisService.getHashObject(prodHashCache, EshopProdDO.class, prodId);
        if (value == null) {
            EshopProdDO prod = eshopProdDao.getValidProdById(prodId);
            if (prod != null) {
                eshopRedisService.setHashObject(prodHashCache, prod, prodId);
            }
            return prod;
        }
        return value;
    }

    public void deleteProdData(Long prodId) {
        eshopRedisService.removeHashValue(prodHashCache, prodId);
    }

    public boolean refreshProdData(Long prodId) {
        if (!eshopRedisService.existHashKey(prodHashCache, prodId)) {
            EshopProdDO prod = eshopProdDao.getValidProdById(prodId);
            if (prod != null) {
                eshopRedisService.setHashObject(prodHashCache, prod, prodId);
            } else {
                return false;
            }
        }
        return true;
    }

    public long getProdSoldNum(Long prodId) {
        if (refreshProdData(prodId)) {
            return eshopRedisService.getHashField(prodHashCache, PROD_SOLD_NUM, Long.class, prodId);
        }
        return -1L;
    }

    public void changeProdSoldNum(Long prodId, Integer num) {
        eshopRedisService.incrementHashField(prodHashCache, PROD_SOLD_NUM, num, prodId);
    }

    public long getProdTotalStocks(Long prodId) {
        if (refreshProdData(prodId)) {
            return eshopRedisService.getHashField(prodHashCache, PROD_STOCK, Long.class, prodId);
        }
        return -1L;
    }

    public void changeProdTotalStocks(Long prodId, Integer num) {
        eshopRedisService.incrementHashField(prodHashCache, PROD_STOCK, num, prodId);
    }

    public EshopProdSkuDO getProdSkuData(Long prodId, Long skuId) {
        EshopProdSkuDO value =
                eshopRedisService.getHashObject(prodSkuHashCache, EshopProdSkuDO.class, prodId, skuId);
        if (value == null) {
            EshopProdSkuDO sku = eshopProdSkuDao.selectById(skuId);
            if (sku != null) {
                eshopRedisService.setHashObject(prodSkuHashCache, sku, prodId, skuId);
            }
            return sku;
        }
        return value;
    }

    public void deleteProdSkuData(Long prodId, Long skuId) {
        eshopRedisService.deleteHashObject(prodSkuHashCache, prodId, skuId);
    }

    public boolean refreshProdSkuData(Long prodId, Long skuId) {
        if (!eshopRedisService.existHashKey(prodSkuHashCache, prodId, skuId)) {
            EshopProdSkuDO sku = eshopProdSkuDao.selectById(skuId);
            if (sku != null) {
                eshopRedisService.setHashObject(prodSkuHashCache, sku, prodId, skuId);
            } else {
                return false;
            }
        }
        return true;
    }

    public long getProdSkuStocks(Long prodId, Long skuId) {
        if (refreshProdSkuData(prodId, skuId)) {
            return eshopRedisService.getHashField(prodSkuHashCache, PROD_SKU_STOCKS, Long.class, prodId, skuId);
        }
        return -1L;
    }

    public void changeProSkuStocks(Long prodId, Long skuId, Integer num) {
        eshopRedisService.incrementHashField(prodSkuHashCache, PROD_SKU_STOCKS, num, prodId, skuId);
    }
}
