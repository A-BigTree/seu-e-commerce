package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("prod_category_prop")
public class ProductCategoryPropDO extends MysqlBaseDO {
    private Long categoryId;
    private Long propId;
    private Long shopId;
    private Integer propType;
}
