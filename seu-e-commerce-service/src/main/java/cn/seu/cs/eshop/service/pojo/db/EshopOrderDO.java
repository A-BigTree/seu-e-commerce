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
    private Long addressId;
    private String prodName;
    private String pic;
    private String orderNumber;
    private String remarks;
    private Integer prodCount;
    private Integer status;
    private Long shopId;
    private String shopName;
    private String deliveryId;  // 发货
    private Long deliveryCost;
    private Integer orderType;
    private Integer closeType;  // 取消
    private Long total;
    private Integer payType;  // 付款
    private Integer userDeleted; // 用户删除
    private Long payTime;     // 付款
    private Long deliveryTime;  // 发货
    private Long completeTime;  // 完成
    private Long cancelTime;    // 取消
    private Long updateTime;   // 更新
}
