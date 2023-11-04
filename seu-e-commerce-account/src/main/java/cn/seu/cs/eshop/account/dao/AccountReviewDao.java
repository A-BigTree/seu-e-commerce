package cn.seu.cs.eshop.account.dao;

import cn.seu.cs.eshop.account.pojo.db.AccountReviewDO;
import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@Mapper
public interface AccountReviewDao extends MysqlBaseDao<AccountReviewDO> {
    default AccountReviewDO selectByAccountId(Long accountId) {
        AccountReviewDO entity = new AccountReviewDO();
        entity.setAccountId(accountId);
        return selectOne(new QueryWrapper<>(entity));
    }
}
