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
@TableName("eshop_basket")
public class EshopBasketDO extends MysqlBaseDO {
    private Long userId;
    private Long prodId;
    private String prodName;
    private Long skuId;
    private String skuName;
    private Long price;
    private Long shopId;
    private Integer prodCount;
    private String pic;
}
