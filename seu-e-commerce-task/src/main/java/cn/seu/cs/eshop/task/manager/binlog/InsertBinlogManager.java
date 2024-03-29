package cn.seu.cs.eshop.task.manager.binlog;

import cn.seu.cs.eshop.service.cache.product.ProdBasketHashCache;
import cn.seu.cs.eshop.service.cache.product.ProdIndexToCCache;
import cn.seu.cs.eshop.service.cache.product.ProdSkusToBCache;
import cn.seu.cs.eshop.service.cache.product.ProdToBCache;
import cn.seu.cs.eshop.service.convert.EshopProductConvert;
import cn.seu.cs.eshop.service.dao.EshopBasketDao;
import cn.seu.cs.eshop.service.dao.EshopOrderItemDao;
import cn.seu.cs.eshop.service.es.EsProductInfoService;
import cn.seu.cs.eshop.service.es.EsProductSearchService;
import cn.seu.cs.eshop.service.pojo.db.*;
import cn.seu.cs.eshop.service.pojo.es.EshopProductInfoIndex;
import cn.seu.cs.eshop.service.pojo.es.ProductEsIndex;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.seu.cs.eshop.service.enums.order.EshopOrderType.CART;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
@Slf4j
public class InsertBinlogManager extends AbstractBinlogManager{
    @Resource
    ProdToBCache prodToBCache;
    @Resource
    ProdSkusToBCache prodSkusToBCache;
    @Resource
    EsProductInfoService esProductInfoService;
    @Resource
    EsProductSearchService esProductSearchService;
    @Resource
    ProdIndexToCCache prodIndexToCCache;
    @Resource
    EshopBasketDao eshopBasketDao;
    @Resource
    EshopOrderItemDao eshopOrderItemDao;
    @Resource
    ProdBasketHashCache prodBasketHashCache;

    @Override
    public void writeProdPropValue(ProductPropValueDO data) {
        log.info("insert:{}", data);
    }

    @Override
    protected void writeEshopProd(EshopProdDO data) {
        prodToBCache.deleteProd(data.getId());
        prodIndexToCCache.removeProdIndex();
        EshopProdDO prod = prodToBCache.getProd(data.getId());
        if (!Objects.equals(prod.getUpdateTime(), data.getUpdateTime())) {
            log.info("Insert Data: {} has changed, skip this", data);
            return;
        }
        List<EshopProdSkuDO> skus = prodSkusToBCache.getProdSkus(data.getId());
        List<EshopProductInfoIndex> indies = skus.stream()
                .map(sku -> EshopProductConvert.convertToEshopProductInfoIndex(prod, sku))
                .toList();
        indies.forEach(index -> esProductInfoService.save(index));
        String skuProperties = skus.stream()
                .map(EshopProdSkuDO::getProperties)
                .collect(Collectors.joining(";"));
        ProductEsIndex productEsIndex = EshopProductConvert.convertToProductEsIndex(prod, skuProperties);
        esProductSearchService.save(productEsIndex);
    }

    @Override
    protected void writeEshopProdSku(EshopProdSkuDO data) {
        prodSkusToBCache.deleteProdSkus(data.getProdId());
    }

    @Override
    protected void writeEshopOrder(EshopOrderDO data) {
        if (data.getOrderType() == CART.getType()) {
            Long userId = data.getUserId();
            String orderNumber = data.getOrderNumber();
            List<EshopOrderItemDO> items = eshopOrderItemDao.selectByOrderId(orderNumber);
            if (CollectionUtils.isNotEmpty(items)) {
                List<Long> prodIds = items.stream()
                        .map(EshopOrderItemDO::getProdId)
                        .toList();
                EshopBasketDO entity = new EshopBasketDO();
                entity.setUserId(userId);
                QueryWrapper<EshopBasketDO> wrapper = new QueryWrapper<>(entity);
                wrapper.in("prod_id", prodIds);
                eshopBasketDao.delete(wrapper);
                prodBasketHashCache.deleteBasketListByUserId(userId);
            }
        }
    }
}
