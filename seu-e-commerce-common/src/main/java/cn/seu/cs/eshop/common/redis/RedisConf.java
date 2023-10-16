package cn.seu.cs.eshop.common.redis;

import cn.seu.cs.eshop.common.constants.RedisConstants;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public interface RedisConf {
    String prefixKey();

    long expirationTime();

    TimeUnit timeUnit();

    default String buildKey(String key) {
        return RedisConstants.ESHOP_PREFIX + prefixKey() + key;
    }
}
