package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("eshop_order")
public class EshopOrderDO extends MysqlBaseDO {
    private Long userId;
    private Long shopId;
    private Long addressId;
    private String prodName;
    private String orderNumber;
    private String remarks;
    private Integer prodCount;
    private Integer status;
    private String deliveryId;
    private Long deliveryCost;
    private Integer orderType;
    private Integer closeType;
    private Long total;
    private Integer payType;
    private Integer userDeleted;
    private Long payTime;
    private Long deliveryTime;
    private Long completeTime;
    private Long cancelTime;
    private Long updateTime;
}
