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
@TableName("eshop_prod_sku")
public class EshopProdSkuDO extends MysqlBaseDO {
    private Long prodId;
    private String properties;
    private String skuName;
    private String skuCode;
    private String pic;
    private Long originPrice;
    private Long price;
    private Integer stocks;
}
