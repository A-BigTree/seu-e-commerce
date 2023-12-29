package cn.seu.cs.eshop.api.redis;

import cn.seu.cs.eshop.common.redis.RedisConf;

import java.util.concurrent.TimeUnit;

import static cn.seu.cs.eshop.common.constants.RedisConstants.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
public enum ApiRedisConfigEnum implements RedisConf {
    apiTokenCache("api:user:token:", 6L, TimeUnit.HOURS),
    imageUniqueIdGenerateKey("unique:id:api:image"),

    ;

    private final String prefix;
    private final long expirationTime;
    private final TimeUnit timeUnit;

    ApiRedisConfigEnum(String prefix) {
        this.prefix = prefix;
        this.expirationTime = DEFAULT_EXPIRATION_TIME;
        this.timeUnit = DEFAULT_EXPIRATION_TIME_UNIT;
    }

    ApiRedisConfigEnum(String prefix, long expirationTime, TimeUnit timeUnit) {
        this.prefix = prefix;
        this.expirationTime = expirationTime;
        this.timeUnit = timeUnit;
    }

    @Override
    public String prefixKey() {
        return this.prefix;
    }

    @Override
    public long expirationTime() {
        return this.expirationTime;
    }

    @Override
    public TimeUnit timeUnit() {
        return this.timeUnit;
    }
}
