package cn.seu.cs.eshop.common.component;

import cn.seu.cs.eshop.common.redis.RedisConf;
import cn.seu.cs.eshop.common.redis.RedisService;
import cn.seu.cs.eshop.common.util.JsonUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public <T> void setHashObject(RedisConf conf, T value, Object... keys) {
        String key = conf.prefixKey().formatted(keys);
        Map<String, String> map = JsonUtils.jsonToMap(JsonUtils.objectToJson(value), String.class, String.class);
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, conf.expirationTime(), conf.timeUnit());
    }

    public <T> T getHashObject(RedisConf conf, Class<T> clazz, Object... keys) {
        String key = conf.prefixKey().formatted(keys);
        HashOperations<String, String, String> operations = redisTemplate.opsForHash();
        Map<String, String> map = operations.entries(key);
        if (map.isEmpty()) {
            return null;
        }
        return JsonUtils.mapToObject(map, clazz);
    }

    public void deleteHashObject(RedisConf conf, Object... keys) {
        String key = conf.prefixKey().formatted(keys);
        redisTemplate.delete(key);
    }

    public <T> void setHashField(RedisConf conf, String field, T value, Object... keys) {
        String key = conf.prefixKey().formatted(keys);
        String json = JsonUtils.objectToJson(value);
        redisTemplate.opsForHash().put(key, field, json);
        redisTemplate.expire(key, conf.expirationTime(), conf.timeUnit());
    }

    public <T> T getHashField(RedisConf conf, String field, Class<T> clazz, Object... keys) {
        String key = conf.prefixKey().formatted(keys);
        String json = (String) redisTemplate.opsForHash().get(key, field);
        return JsonUtils.jsonToObject(json, clazz);
    }

    public void deleteHashField(RedisConf conf, String field, Object... keys) {
        String key = conf.prefixKey().formatted(keys);
        redisTemplate.opsForHash().delete(key, field);
    }

    public long incrementHashField(RedisConf conf, String field, long num, Object... keys) {
        String key = conf.prefixKey().formatted(keys);
        return redisTemplate.opsForHash().increment(key, field, num);
    }

    public Boolean existHashKey(RedisConf conf, Object...keys) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(conf.prefixKey().formatted(keys)));
    }

    public void removeHashValue(RedisConf conf, Object...keys) {
        redisTemplate.delete(conf.prefixKey().formatted(keys));
    }
}
