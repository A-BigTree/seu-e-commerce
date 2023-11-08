package cn.seu.cs.eshop.common.component;

import cn.seu.cs.eshop.common.exception.EshopException;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/23
 */
@Component
@Aspect
@Slf4j
public class RpcMonitorAspect {
    @Around("@annotation(cn.seu.cs.eshop.common.aop.RpcMonitor)")
    @SuppressWarnings("unchecked")
    public Object rpcMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<? extends BaseResponseInterface<Object>> clazz =
                (Class<? extends BaseResponseInterface<Object>>) method.getGenericReturnType();
        // TODO RPC监控
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            if (e instanceof EshopException) {
                return ResponseBuilderUtils.buildFailResponse(clazz, e.getMessage(), null);
            } else {
                log.error("Method: {} exec error, e: ", method, e);
                throw e;
            }
        }
    }
}
