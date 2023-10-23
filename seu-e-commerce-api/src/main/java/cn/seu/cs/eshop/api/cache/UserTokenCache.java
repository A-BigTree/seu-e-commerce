package cn.seu.cs.eshop.api.cache;

import cn.seu.cs.eshop.account.sdk.entity.dto.EshopSessionDTO;
import cn.seu.cs.eshop.account.sdk.entity.dto.UserBaseDTO;
import cn.seu.cs.eshop.common.redis.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import static cn.seu.cs.eshop.api.redis.ApiRedisConfigEnum.apiTokenCache;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/23
 */
@Component
public class UserTokenCache {
    @Resource
    RedisService redisService;

    public UserBaseDTO getUserTokenInfo(String token) {
        return redisService.getObjectValue(token, apiTokenCache, UserBaseDTO.class);
    }

    public void setUserTokenInfo(String token, UserBaseDTO user) {
        redisService.setObjectValue(token, user, apiTokenCache);
    }

    public void removeToken(String token) {
        redisService.removeValue(token, apiTokenCache);
    }
}
