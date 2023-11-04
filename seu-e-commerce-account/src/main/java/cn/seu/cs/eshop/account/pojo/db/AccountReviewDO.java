package cn.seu.cs.eshop.account.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("account_review")
public class AccountReviewDO extends MysqlBaseDO {
    private Long accountId;
    private String remark;
    private String modifier;
}
