package cn.seu.cs.eshop.im.redis;

import cn.seu.cs.eshop.common.redis.RedisConf;

import java.util.concurrent.TimeUnit;

import static cn.seu.cs.eshop.common.constants.RedisConstants.DEFAULT_EXPIRATION_TIME;
import static cn.seu.cs.eshop.common.constants.RedisConstants.DEFAULT_EXPIRATION_TIME_UNIT;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
public enum ImRedisConfigEnum  implements RedisConf {
    imSessionIdGenerateKey("im:session:unique:id"),
    imMessageIdGenerateKey("im:message:unique:id:", 10L, TimeUnit.MINUTES),

    ;
    private final String prefix;
    private final long expirationTime;
    private final TimeUnit timeUnit;

    ImRedisConfigEnum(String prefix) {
        this.prefix = prefix;
        this.expirationTime = DEFAULT_EXPIRATION_TIME;
        this.timeUnit = DEFAULT_EXPIRATION_TIME_UNIT;
    }

    ImRedisConfigEnum(String prefix, long expirationTime, TimeUnit timeUnit) {
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
