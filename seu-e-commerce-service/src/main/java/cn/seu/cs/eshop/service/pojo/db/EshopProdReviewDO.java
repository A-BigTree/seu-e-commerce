package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("eshop_prod_review")
public class EshopProdReviewDO extends MysqlBaseDO {
    private Long prodId;
    private String modifier;
    private String remark;
    private Integer status;
}
