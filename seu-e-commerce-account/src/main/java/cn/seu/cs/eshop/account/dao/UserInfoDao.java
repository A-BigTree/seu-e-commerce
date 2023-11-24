package cn.seu.cs.eshop.account.dao;

import cn.seu.cs.eshop.account.pojo.db.UserInfoDO;
import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cs.seu.cs.eshop.common.sdk.enums.RegisterStateEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.apache.commons.lang3.StringUtils;
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
        entity.setState(RegisterStateEnum.REGISTER_SUCCESS.getState());
        QueryWrapper<UserInfoDO> wrapper = new QueryWrapper<>(entity);
        return selectOne(wrapper);
    }

    default IPage<UserInfoDO> selectPagesByConditions(UserInfoDO entity, String nickname, PageDTO pageDTO) {
        QueryWrapper<UserInfoDO> wrapper = new QueryWrapper<>(entity);
        if (!StringUtils.isEmpty(nickname)) {
            wrapper.like("nickname", nickname);
        }
        return selectPage(pageDTO, wrapper);
    }
}
