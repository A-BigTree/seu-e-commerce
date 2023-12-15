package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("eshop_order_review")
public class EshopOrderReviewDO extends MysqlBaseDO {
    private Long orderId;
    private String modifier;
    private String remark;
    private Integer status;
    private Integer oldStatus;
}
