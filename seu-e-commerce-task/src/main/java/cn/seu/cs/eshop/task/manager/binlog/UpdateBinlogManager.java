package cn.seu.cs.eshop.task.manager.binlog;

import cn.seu.cs.eshop.service.cache.product.ProdSkusToBCache;
import cn.seu.cs.eshop.service.cache.product.ProdToBCache;
import cn.seu.cs.eshop.service.convert.EshopProductConvert;
import cn.seu.cs.eshop.service.es.EsProductInfoService;
import cn.seu.cs.eshop.service.es.EsProductSearchService;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import cn.seu.cs.eshop.service.pojo.es.EshopProductInfoIndex;
import cn.seu.cs.eshop.service.pojo.es.ProductEsIndex;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
@Slf4j
public class UpdateBinlogManager extends AbstractBinlogManager{
    @Resource
    ProdToBCache prodToBCache;
    @Resource
    ProdSkusToBCache prodSkusToBCache;
    @Resource
    EsProductInfoService esProductInfoService;
    @Resource
    EsProductSearchService esProductSearchService;

    @Override
    public void writeProdPropValue(ProductPropValueDO data) {
        log.info("update: {}", data);
    }

    @Override
    protected void writeEshopProd(EshopProdDO data) {
        prodToBCache.deleteProd(data.getId());
        EshopProdDO prod = prodToBCache.getProd(data.getId());
        if (!Objects.equals(prod.getUpdateTime(), data.getUpdateTime())) {
            log.info("Updated Data: {} has changed, skip this", data);
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
        prodToBCache.deleteProd(data.getId());
    }
}
