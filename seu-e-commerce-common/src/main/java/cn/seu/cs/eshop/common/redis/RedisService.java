package cn.seu.cs.eshop.common.redis;

import cn.seu.cs.eshop.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public interface RedisService {
    String getValue(String key, RedisConf redisConf);

    void setValue(String key, String value, RedisConf redisConf);

    Boolean removeValue(String key, RedisConf redisConf);


    default <T> T getObjectValue(String key, RedisConf redisConf, Class<T> clazz) {
        String res = getValue(key, redisConf);
        if (StringUtils.isEmpty(res)) {
            return null;
        }
        return JsonUtils.jsonToObject(res, clazz);
    }

    default <T> void setObjectValue(String key, T value, RedisConf redisConf) {
        String json = JsonUtils.objectToJson(value);
        setValue(key, json, redisConf);
    }

    default <T> List<T> getListValue(String key, RedisConf redisConf, Class<T> clazz) {
        String res = getValue(key, redisConf);
        if (StringUtils.isEmpty(res)) {
            return new ArrayList<>();
        }
        return JsonUtils.jsonToList(res, clazz);
    }
}
