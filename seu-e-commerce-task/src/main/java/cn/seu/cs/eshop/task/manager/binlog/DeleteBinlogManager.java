package cn.seu.cs.eshop.task.manager.binlog;

import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
@Slf4j
public class DeleteBinlogManager extends AbstractBinlogManager{
    @Override
    public void writeProdPropValue(ProductPropValueDO data) {
        log.info("delete: {}", data);
    }
}
