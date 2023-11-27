package cn.seu.cs.eshop.task.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Slf4j
@Aspect
@Component
public class KafkaConsumerMonitorAspect {
    @Around("@annotation(cn.seu.cs.eshop.task.annotation.KafkaConsumerMonitor)")
    public Object consumerMonitor(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object result = null;
        try {
            result = joinPoint.proceed();
            Object[] values = joinPoint.getArgs();
            for (Object value: values) {
                if (value instanceof ConsumerRecord<?,?>) {
                    log.info("Message consume successfully: {}", value);
                }
            }
        } catch (Throwable e) {
            log.error("Method: {} consume message error, e: ", method, e);
        }
        return result;
    }
}
