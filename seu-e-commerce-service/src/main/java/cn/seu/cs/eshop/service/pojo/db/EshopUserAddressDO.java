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
@TableName("eshop_user_address")
public class EshopUserAddressDO extends MysqlBaseDO {
    private Long userId;
    private String receiver;
    private String mobile;
    private Long areaId;
    private String address;
    private Integer defaultAddress;
}
