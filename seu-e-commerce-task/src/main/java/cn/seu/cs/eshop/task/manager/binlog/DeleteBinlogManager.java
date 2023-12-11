package cn.seu.cs.eshop.task.manager.binlog;

import cn.seu.cs.eshop.service.cache.product.ProdSkusToBCache;
import cn.seu.cs.eshop.service.cache.product.ProdToBCache;
import cn.seu.cs.eshop.service.es.EsProductInfoService;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
@Slf4j
public class DeleteBinlogManager extends AbstractBinlogManager{
    @Resource
    ProdToBCache prodToBCache;
    @Resource
    ProdSkusToBCache prodSkusToBCache;
    @Resource
    EsProductInfoService esProductInfoService;

    @Override
    public void writeProdPropValue(ProductPropValueDO data) {
        log.info("delete: {}", data);
    }

    @Override
    protected void writeEshopProd(EshopProdDO data) {
        prodToBCache.deleteProd(data.getId());
    }

    @Override
    protected void writeEshopProdSku(EshopProdSkuDO data) {
        prodSkusToBCache.deleteProdSkus(data.getProdId());
    }
}
