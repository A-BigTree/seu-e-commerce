package cn.seu.cs.eshop.common.component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/23
 */
@Component
@Aspect
@Slf4j
public class RpcMonitorAspect {
    @Around("@annotation(cn.seu.cs.eshop.common.aop.RpcMonitor)")
    public Object rpcMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        // TODO RPC监控
        return joinPoint.proceed();
    }
}
