package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/6
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("prod_category")
public class ProductCategoryDO extends MysqlBaseDO {
    private Long shopId;
    private Long parentId;
    private String categoryName;
    private Integer status;
    private Integer categoryLevel;
}
