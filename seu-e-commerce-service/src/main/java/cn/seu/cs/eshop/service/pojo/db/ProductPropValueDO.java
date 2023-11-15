package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductPropValueDO extends MysqlBaseDO {
    private String valueName;
    private Long propId;
    private Long shopId;
}
