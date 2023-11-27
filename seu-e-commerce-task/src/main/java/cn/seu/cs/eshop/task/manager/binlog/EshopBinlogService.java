package cn.seu.cs.eshop.task.manager.binlog;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import cn.seu.cs.eshop.common.util.JsonUtils;
import cs.seu.cs.eshop.common.sdk.entity.dto.MaxwellMessageDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static cs.seu.cs.eshop.common.sdk.enums.DeletedStateEnum.DELETED;
import static cs.seu.cs.eshop.common.sdk.enums.MaxwellTypeEnum.getBinlogType;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
public class EshopBinlogService {
    @Resource
    InsertBinlogManager insertBinlogManager;
    @Resource
    UpdateBinlogManager updateBinlogManager;
    @Resource
    DeleteBinlogManager deleteBinlogManager;

    public final void handleBinlogData(MaxwellMessageDTO binlog) {
        switch (getBinlogType(binlog.getType())) {
            case INSERT -> insertBinlogManager.handleMysqlData(binlog);
            case DELETE -> deleteBinlogManager.handleMysqlData(binlog);
            case UPDATE -> {
                MysqlBaseDO base = JsonUtils.snakeCaseMapToObject(binlog.getData(), MysqlBaseDO.class);
                if (base.getDeleted() == DELETED.getState()) {
                    deleteBinlogManager.handleMysqlData(binlog);
                } else {
                    updateBinlogManager.handleMysqlData(binlog);
                }
            }
        }
    }
}
