package cn.seu.cs.eshop.service.redis;

import cn.seu.cs.eshop.common.redis.RedisConf;

import java.util.concurrent.TimeUnit;

import static cn.seu.cs.eshop.common.constants.RedisConstants.DEFAULT_EXPIRATION_TIME;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/6
 */
public enum ServiceRedisConfEnum implements RedisConf {
    prodCategory("prod:category:", 5L, TimeUnit.MINUTES),
    prodSkuCodeGenerate("prod:sku:code:generate"),
    prodToBCache("prod:tob:cache:", 3L, TimeUnit.MINUTES),
    prodSkusToBCache("prod:tob:skus:cache:", 3L, TimeUnit.MINUTES),


    ;
    private final String prefix;
    private final long expirationTime;
    private final TimeUnit timeUnit;

    ServiceRedisConfEnum(String prefix) {
        this.prefix = prefix;
        this.expirationTime = DEFAULT_EXPIRATION_TIME;
        this.timeUnit = TimeUnit.SECONDS;
    }

    ServiceRedisConfEnum(String prefix, long expirationTime) {
        this.prefix = prefix;
        this.expirationTime = expirationTime;
        this.timeUnit = TimeUnit.SECONDS;
    }

    ServiceRedisConfEnum(String prefix, long expirationTime, TimeUnit timeUnit) {
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
