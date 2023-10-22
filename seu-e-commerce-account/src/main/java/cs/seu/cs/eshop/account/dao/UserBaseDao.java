package cs.seu.cs.eshop.account.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cs.seu.cs.eshop.account.pojo.db.UserBaseDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Mapper
public interface UserBaseDao extends MysqlBaseDao<UserBaseDO> {
    default UserBaseDO selectByAccountAndRole(String account, String password, Integer roleType) {
        UserBaseDO entity = MysqlUtils.buildEffectEntity(new UserBaseDO());
        entity.setAccount(account);
        entity.setPassword(password);
        entity.setRoleType(roleType);
        QueryWrapper<UserBaseDO> wrapper = new QueryWrapper<>(entity);
        return selectOne(wrapper);
    }
}
