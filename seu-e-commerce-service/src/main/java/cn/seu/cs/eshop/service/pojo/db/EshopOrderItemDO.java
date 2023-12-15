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
@TableName("eshop_order_item")
public class EshopOrderItemDO extends MysqlBaseDO{
    private Long orderId;
    private String orderNumber;
    private Long prodId;
    private Long skuId;
    private Long shopId;
    private Integer prodCount;
    private Integer price;
    private String prodName;
    private String skuName;
    private String pic;
    private Long userId;
    private Integer commStatus;
}
