package cn.seu.cs.eshop.account.cache;

import cn.seu.cs.eshop.account.dao.UserInfoDao;
import cn.seu.cs.eshop.account.pojo.db.UserInfoDO;
import cn.seu.cs.eshop.common.redis.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.account.redis.AccountRedisConfEnum.accountUserSession;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
@Component
public class UserInfoCache {
    @Resource
    RedisService eshopRedisService;
    @Resource
    UserInfoDao userInfoDao;

    public UserInfoDO getUserInfo(Long id) {
        if (id == null) {
            return null;
        }
        UserInfoDO user = eshopRedisService.getObjectValue(id.toString(),
                accountUserSession, UserInfoDO.class);
        if (user == null) {
            UserInfoDO userInfoDO = userInfoDao.selectById(id);
            if (userInfoDO == null) {
                return null;
            }
            setSession(userInfoDO);
            return userInfoDO;
        }
        return user;
    }

    public void setSession(UserInfoDO userInfoDO) {
        eshopRedisService.setObjectValue(userInfoDO.getId().toString(), userInfoDO, accountUserSession);
    }

    public void remove(long id) {
        eshopRedisService.removeValue(Long.toString(id), accountUserSession);
    }
}
