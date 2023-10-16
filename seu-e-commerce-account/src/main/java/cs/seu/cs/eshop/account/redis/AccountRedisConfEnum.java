package cs.seu.cs.eshop.account.redis;

import cn.seu.cs.eshop.common.redis.RedisConf;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public enum AccountRedisConfEnum implements RedisConf {
    accountRedisTest("acc:test:", 30L)


    ;
    private final String prefix;
    private final long expirationTime;

    AccountRedisConfEnum(String prefix, long expirationTime) {
        this.prefix = prefix;
        this.expirationTime = expirationTime;
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
        return TimeUnit.SECONDS;
    }
}
