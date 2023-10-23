package cn.seu.cs.eshop.api.cache;

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

    public Long getUserTokenInfo(String token) {
        return redisService.getObjectValue(token, apiTokenCache, Long.class);
    }

    public void setUserTokenInfo(String token, Long id) {
        redisService.setObjectValue(token, id, apiTokenCache);
    }

    public void removeToken(String token) {
        redisService.removeValue(token, apiTokenCache);
    }
}
