package cn.seu.cs.eshop.account.dao;

import cn.seu.cs.eshop.account.pojo.db.UserInfoDO;
import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Mapper
public interface UserInfoDao extends MysqlBaseDao<UserInfoDO> {
    default UserInfoDO selectByAccountAndRole(String account, String password, Integer roleType) {
        UserInfoDO entity = new UserInfoDO();
        entity.setAccount(account);
        entity.setPassword(password);
        entity.setRoleType(roleType);
        QueryWrapper<UserInfoDO> wrapper = new QueryWrapper<>(entity);
        return selectOne(wrapper);
    }
}
