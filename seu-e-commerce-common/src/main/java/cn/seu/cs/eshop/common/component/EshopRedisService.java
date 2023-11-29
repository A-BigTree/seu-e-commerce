package cn.seu.cs.eshop.common.component;

import cn.seu.cs.eshop.common.redis.RedisConf;
import cn.seu.cs.eshop.common.redis.RedisService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Service
public class EshopRedisService implements RedisService {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Override
    public String getValue(String key, RedisConf redisConf) {
        return redisTemplate.opsForValue().get(redisConf.buildKey(key));
    }

    @Override
    public void setValue(String key, String value, RedisConf redisConf) {
        redisTemplate.opsForValue().set(redisConf.buildKey(key), value,
                redisConf.expirationTime(), redisConf.timeUnit());
    }

    @Override
    public Boolean removeValue(String key, RedisConf redisConf) {
        return redisTemplate.delete(redisConf.buildKey(key));
    }

    @Override
    public Boolean existKey(String key, RedisConf redisConf) {
        return !StringUtils.isEmpty(key) && Boolean.TRUE.equals(redisTemplate.hasKey(redisConf.buildKey(key)));
    }

    @Override
    public void refreshExpirationTime(String key, RedisConf redisConf) {
        boolean isExist = existKey(key, redisConf);
        if (isExist) {
            redisTemplate.expire(redisConf.buildKey(key), redisConf.expirationTime(), redisConf.timeUnit());
        }
    }
}
