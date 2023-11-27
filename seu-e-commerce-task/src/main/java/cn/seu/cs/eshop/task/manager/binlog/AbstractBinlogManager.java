package cn.seu.cs.eshop.task.manager.binlog;

import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import cs.seu.cs.eshop.common.sdk.entity.dto.MaxwellMessageDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static cn.seu.cs.eshop.task.enums.EshopMysqlTableEnum.PROD_PROP_VALUE;
import static cn.seu.cs.eshop.task.enums.EshopMysqlTableEnum.getDatabaseTable;

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
            // Add here

            default -> log.info("No this table function: {}.{}", binlog.getDatabase(), binlog.getTable());
        }
    }

    protected abstract void writeProdPropValue(ProductPropValueDO data);
}
