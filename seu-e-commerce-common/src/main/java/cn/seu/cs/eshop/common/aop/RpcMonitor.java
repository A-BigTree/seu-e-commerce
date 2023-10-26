package cn.seu.cs.eshop.common.aop;

import java.lang.annotation.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcMonitor {
    boolean printLog() default true;
    boolean persist() default false;
}
