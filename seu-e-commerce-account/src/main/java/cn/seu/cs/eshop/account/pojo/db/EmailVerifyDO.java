package cn.seu.cs.eshop.account.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("email_verify")
public class EmailVerifyDO extends MysqlBaseDO {
    private String account;
    private String verifyCode;
}
