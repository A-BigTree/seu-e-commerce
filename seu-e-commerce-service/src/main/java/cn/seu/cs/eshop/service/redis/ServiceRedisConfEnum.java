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
    prodIndexToCCache("prod:toc:index:cache:", 5L, TimeUnit.MINUTES),
    orderAreaLevelCache("order:address:level:cache:", 2L, TimeUnit.HOURS),
    orderAreaIdCache("order:address:id:cache:", 2L, TimeUnit.HOURS),
    orderUserAddressCache("order:user:address:cache:", 5L, TimeUnit.MINUTES),
    orderUserAddressIdCache("order:user:address:id:cache:", 30L, TimeUnit.SECONDS),
    basketListUserIdCache("basket:list:user:id:cache:", 30L, TimeUnit.MINUTES),
    basketProdSkuHashCache("basket:prod:sku:hash:cache:%s", 30L, TimeUnit.MINUTES),
    prodHashCache("prod:hash:cache:%s", 30L, TimeUnit.MINUTES),
    prodSkuHashCache("prod:sku:hash:cache:%s:%s", 30L, TimeUnit.MINUTES),

    // 分布式锁配置
    basketIdRedissonLock("basket:id:redisson:lock:%s", 5L, TimeUnit.SECONDS),
    prodIdRedissonLock("prod:id:redisson:lock:%s", 5L, TimeUnit.SECONDS),
    prodSkuIdRedissonLock("prod:sku:id:redisson:lock:%s:%s", 5L, TimeUnit.SECONDS),

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
