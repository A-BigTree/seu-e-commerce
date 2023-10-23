package cn.seu.cs.eshop.account.cache;

import cn.seu.cs.eshop.account.dao.UserBaseDao;
import cn.seu.cs.eshop.account.pojo.db.UserBaseDO;
import cn.seu.cs.eshop.account.sdk.entity.dto.EshopSessionDTO;
import cn.seu.cs.eshop.account.sdk.entity.dto.UserBaseDTO;
import cn.seu.cs.eshop.common.redis.RedisService;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

import static cn.seu.cs.eshop.account.redis.AccountRedisConfEnum.accountUserSession;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
@Component
public class UserSessionCache {
    @Resource
    RedisService eshopRedisService;
    @Resource
    UserBaseDao userBaseDao;

    public EshopSessionDTO getSession(Long id) {
        if(id == null) {
            return null;
        }
        EshopSessionDTO session = eshopRedisService.getObjectValue(id.toString(), accountUserSession,
                EshopSessionDTO.class);
        if (session == null) {
            UserBaseDO userBaseDO = userBaseDao.selectById(id);
            if (userBaseDO == null) {
                return null;
            }
            UserBaseDTO userBaseDTO = UserBaseDTO.builder()
                    .id(userBaseDO.getId())
                    .nickname(userBaseDO.getNickname())
                    .image(Strings.EMPTY)
                    .account(userBaseDO.getAccount())
                    .roleType(userBaseDO.getRoleType())
                    .build();
            String token = UUID.randomUUID().toString() + userBaseDO.getId();
            session = EshopSessionDTO.builder()
                    .id(userBaseDO.getId())
                    .token(token)
                    .user(userBaseDTO)
                    .slots(new HashMap<>())
                    .build();
            setSession(session);
        }
        return session;
    }

    public void setSession(EshopSessionDTO session) {
        eshopRedisService.setObjectValue(session.getId().toString(), session, accountUserSession);
    }
}
