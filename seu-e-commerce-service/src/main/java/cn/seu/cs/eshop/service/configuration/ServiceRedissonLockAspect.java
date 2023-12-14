package cn.seu.cs.eshop.service.configuration;

import cn.seu.cs.eshop.service.annotation.RedissonLock;
import cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@Component
@Aspect
@Slf4j
public class ServiceRedissonLockAspect {
    @Around("@annotation(cn.seu.cs.eshop.service.annotation.RedissonLock)")
    public Object redissonLockAspect(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        ServiceRedisConfEnum config = redissonLock.config();

        return null;
    }
}
