package cn.seu.cs.eshop.account.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("login")
public class UserInfoDO extends MysqlBaseDO {
    private String account;
    private String password;
    private String nickname;
    private String image;
    private String phoneNumber;
    private Integer roleType;
}
