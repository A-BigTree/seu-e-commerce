package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.cache.product.ProdBasketHashCache;
import cn.seu.cs.eshop.service.cache.product.ProdHashCache;
import cn.seu.cs.eshop.service.convert.ProdBasketConvert;
import cn.seu.cs.eshop.service.dao.EshopBasketDao;
import cn.seu.cs.eshop.service.pojo.db.EshopBasketDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.redisson.EshopRedissonLockService;
import cn.seu.cs.eshop.service.sdk.product.basket.dto.EshopProdBasketDTO;
import cn.seu.cs.eshop.service.sdk.product.basket.req.ListUserProdBasketResponse;
import cn.seu.cs.eshop.service.sdk.product.basket.req.UpdateUserProdBasketRequest;
import cn.seu.cs.eshop.service.service.AbstractCrudService;
import cn.seu.cs.eshop.service.service.product.ProdBasketService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import cs.seu.cs.eshop.common.sdk.exception.EshopException;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.basketIdRedissonLock;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildFailResponse;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@Service
public class ProdBasketServiceImpl extends AbstractCrudService<EshopProdBasketDTO> implements ProdBasketService {
    @Resource
    private ProdBasketHashCache prodBasketHashCache;
    @Resource
    private ProdHashCache prodHashCache;
    @Resource
    private EshopBasketDao eshopBasketDao;
    @Resource
    private EshopRedissonLockService eshopRedissonLockService;

    @Override
    public long insert(EshopProdBasketDTO data) {
        EshopBasketDO origin = eshopBasketDao.getBasketByUserIdAndProdId(data.getUserId(), data.getProdId());
        Long id = 0L;
        if (origin == null) { // 新增
            EshopBasketDO entity = ProdBasketConvert.convertToEshopBasketDO(data);
            MysqlUtils.buildEffectEntity(entity);
            eshopBasketDao.insert(entity);
            prodBasketHashCache.deleteBasketListByUserId(data.getUserId());
            id = entity.getId();
        } else if (Objects.equals(origin.getSkuId(), data.getSkuId())) { // 购物车中已存在
            throw new EshopException("规格商品已在购物车中~");
        } else { // 修改规格
            origin.setSkuId(data.getSkuId());
            origin.setSkuName(data.getSkuName());
            origin.setPrice(data.getPrice());
            origin.setCreateTime(TimeUtils.getCurrentTime());
            eshopBasketDao.updateById(origin);
            prodBasketHashCache.updateBasketData(origin);
            id = origin.getId();
        }
        return id;
    }

    @Override
    public long delete(EshopProdBasketDTO data) {
        throw new EshopException("请求参数不正确！");
    }

    @Override
    public long update(EshopProdBasketDTO data) {
        throw new EshopException("请求参数不正确！");
    }

    @Override
    public BaseResponse getUserProdBasketCount(Long userId) {
        List<EshopBasketDO> baskets = prodBasketHashCache.getBasketListByUserId(userId);
        int num = 0;
        if (CollectionUtils.isNotEmpty(baskets)) {
            num = baskets.size();
        }
        return buildSuccessResponse(BaseResponse.class, Integer.toString(num));
    }

    @Override
    public ListUserProdBasketResponse listUserProdBasket(Long userId) {
        List<EshopBasketDO> baskets = prodBasketHashCache.getBasketListByUserId(userId);
        List<EshopProdBasketDTO> data = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(baskets)) {
            data = baskets.stream()
                    .map(basket -> {
                        EshopProdBasketDTO res = ProdBasketConvert.convertToEshopProdBasketDTO(basket);
                        EshopProdDO prod = prodHashCache.getProdData(res.getProdId());
                        EshopProdSkuDO sku = prodHashCache.getProdSkuData(res.getProdId(), res.getSkuId());
                        EshopBasketDO bask = prodBasketHashCache.getBasketData(basket.getId());
                        if (prod == null || sku == null) {
                            res.setStatus(0);
                            return res;
                        }
                        if (sku.getStocks() == 0) {
                            res.setStatus(2);
                            return res;
                        }
                        res.setStatus(1);
                        res.setProdCount(bask.getProdCount());
                        res.setPrice(sku.getPrice());
                        res.setSkuName(sku.getSkuName());
                        return res;
                    })
                    .toList();
        }
        return buildSuccessResponse(ListUserProdBasketResponse.class, data);
    }

    @Override
    @Transactional
    public BaseResponse updateUserProdBasket(UpdateUserProdBasketRequest request) {
        long id = crudOperation(request);
        return buildSuccessResponse(BaseResponse.class, Long.toString(id));
    }

    @Override
    @Transactional
    public BaseResponse changeProdBasketCount(Long basketId, Integer count) {
        RLock lock = eshopRedissonLockService.getLock(basketIdRedissonLock, basketId);
        lock.lock(basketIdRedissonLock.expirationTime(), basketIdRedissonLock.timeUnit());
        try {
            long res = prodBasketHashCache.changeBasketCount(basketId, count);
            if (res < 0) {
                throw new EshopException("商品数量不能为0 或 购物车商品不存在");
            }
            EshopBasketDO entity = new EshopBasketDO();
            entity.setId(basketId);
            entity.setProdCount((int) res);
            eshopBasketDao.updateById(entity);
            return buildSuccessResponse(BaseResponse.class, Long.toString(basketId));
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public BaseResponse batchDeleteBaskets(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new EshopException("删除ID不能为空");
        }
        ids.forEach(id -> prodBasketHashCache.deleteBasketData(id));
        eshopBasketDao.deleteBatchIds(ids);
        return buildSuccessResponse(BaseResponse.class, "OK");
    }
}
