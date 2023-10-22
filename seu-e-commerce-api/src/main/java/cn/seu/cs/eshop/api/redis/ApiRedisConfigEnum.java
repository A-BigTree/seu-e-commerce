package cn.seu.cs.eshop.api.redis;

import cn.seu.cs.eshop.common.redis.RedisConf;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
public enum ApiRedisConfigEnum implements RedisConf {
    apiTokenCache("api:user:token:", 30L, TimeUnit.MINUTES),

    ;

    private final String prefix;
    private final long expirationTime;
    private final TimeUnit timeUnit;

    ApiRedisConfigEnum(String prefix, long expirationTime) {
        this.prefix = prefix;
        this.expirationTime = expirationTime;
        this.timeUnit = TimeUnit.SECONDS;
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
