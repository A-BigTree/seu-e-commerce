package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("prod_prop")
public class ProductPropDO extends MysqlBaseDO {
    private String propName;
    private Integer propType;
    private Long shopId;
    private Integer selfAdd;
}
