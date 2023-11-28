package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@EqualsAndHashCode(callSuper = true)
@TableName("eshop_prod")
@Data
public class EshopProdDO extends MysqlBaseDO {
    private String prodName;
    private Long shopId;
    private Integer status;
    private Long originPrice;
    private Long price;
    private Long categoryId;
    private Integer totalStocks;
    private Integer soldNum;
    private String brief;
    private String content;
    private String pic;
    private String images;
    private Integer deliveryMode;
    private Integer deliveryPrice;
    private Long updateTime;
}
