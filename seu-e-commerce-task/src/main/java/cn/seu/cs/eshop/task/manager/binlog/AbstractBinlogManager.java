package cn.seu.cs.eshop.task.manager.binlog;

import cn.seu.cs.eshop.service.pojo.db.EshopOrderDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import cs.seu.cs.eshop.common.sdk.entity.dto.MaxwellMessageDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static cn.seu.cs.eshop.task.enums.EshopMysqlTableEnum.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Slf4j
public abstract class AbstractBinlogManager {

    protected void handleMysqlData(MaxwellMessageDTO binlog) {
        Map<String, String> data = binlog.getData();
        switch (getDatabaseTable(binlog.getDatabase(), binlog.getTable())) {
            case PROD_PROP_VALUE -> writeProdPropValue(PROD_PROP_VALUE.getData(data));
            case ESHOP_PROD -> writeEshopProd(ESHOP_PROD.getData(data));
            case ESHOP_PROD_SKU -> writeEshopProdSku(ESHOP_PROD_SKU.getData(data));
            case ESHOP_ORDER -> writeEshopOrder(ESHOP_ORDER.getData(data));
            // Add here

            default -> log.info("No this table function: {}.{}", binlog.getDatabase(), binlog.getTable());
        }
    }

    protected abstract void writeProdPropValue(ProductPropValueDO data);
    protected abstract void writeEshopProd(EshopProdDO data);
    protected abstract void writeEshopProdSku(EshopProdSkuDO data);
    protected abstract void writeEshopOrder(EshopOrderDO data);
}
