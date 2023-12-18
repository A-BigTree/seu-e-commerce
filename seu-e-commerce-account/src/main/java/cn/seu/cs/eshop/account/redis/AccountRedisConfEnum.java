package cn.seu.cs.eshop.account.redis;

import cn.seu.cs.eshop.common.redis.RedisConf;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public enum AccountRedisConfEnum implements RedisConf {
    accountRedisTest("acc:test:", 30L),
    accountEmailVerify("acc:email:verify:", 3L, TimeUnit.MINUTES), //邮箱验证缓存
    accountUserSession("acc:user:session:", 2L, TimeUnit.HOURS), // 用户Session缓存


    ;
    private final String prefix;
    private final long expirationTime;
    private final TimeUnit timeUnit;

    AccountRedisConfEnum(String prefix, long expirationTime) {
        this.prefix = prefix;
        this.expirationTime = expirationTime;
        this.timeUnit = TimeUnit.SECONDS;
    }

    AccountRedisConfEnum(String prefix, long expirationTime, TimeUnit timeUnit) {
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
