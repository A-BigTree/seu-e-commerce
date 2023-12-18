package cn.seu.cs.eshop.common.component;

import cn.seu.cs.eshop.common.redis.RedisConf;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
public class EshopRedisSessionService {
    @Resource
    RedisTemplate<String, Long> sessionTemplate;

    public Long generateUniqueId(RedisConf redisConf) {
        return sessionTemplate.opsForValue().increment(redisConf.prefixKey());
    }

    public <T> Long generateUniqueId(RedisConf redisConf, T key) {
        Long res = sessionTemplate.opsForValue().increment(redisConf.buildKey(key.toString()));
        sessionTemplate.expire(redisConf.buildKey(redisConf.buildKey(key.toString())), redisConf.expirationTime(), redisConf.timeUnit());
        return res;
    }
}
