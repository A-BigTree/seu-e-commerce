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
@TableName("eshop_prod_comm")
public class EshopProdCommDO extends MysqlBaseDO {
    private Long userId;
    private Long prodId;
    private Long orderItemId;
    private String content;
    private Integer score;
    private Integer status;
    private String images;
    private Integer commType;
    private Integer evaluate;
}
