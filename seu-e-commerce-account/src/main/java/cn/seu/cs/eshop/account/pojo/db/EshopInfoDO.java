package cn.seu.cs.eshop.account.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("shop_info")
public class EshopInfoDO extends MysqlBaseDO {
    private String shopDesc;
    private String shopName;
    private String shopImage;
}
