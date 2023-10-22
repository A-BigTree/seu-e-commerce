package cs.seu.cs.eshop.account.cache;

import cn.seu.cs.eshop.account.sdk.entity.dto.EshopSessionDTO;
import cn.seu.cs.eshop.common.redis.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static cs.seu.cs.eshop.account.redis.AccountRedisConfEnum.accountUserSession;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
@Component
public class UserSessionCache {
    @Resource
    RedisService eshopRedisService;

    public EshopSessionDTO getSession(Long id) {
        return eshopRedisService.getObjectValue(id.toString(), accountUserSession, EshopSessionDTO.class);
    }

    public void setSession(EshopSessionDTO session) {
        eshopRedisService.setObjectValue(session.getId().toString(), session, accountUserSession);
    }
}
