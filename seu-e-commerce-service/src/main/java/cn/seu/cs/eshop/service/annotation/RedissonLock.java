package cn.seu.cs.eshop.service.annotation;

import cn.seu.cs.eshop.common.redis.RedisConf;
import cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum;

import java.lang.annotation.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {
    ServiceRedisConfEnum config();
}
