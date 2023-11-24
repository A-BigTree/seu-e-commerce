package cn.seu.cs.eshop.api.annotation;

import cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum;

import java.lang.annotation.*;

/**
 * API 统一监控注解
 *
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiMonitor {
    boolean isAuthor() default true;
    UserRoleEnum[] roleType() default UserRoleEnum.DEFAULT;
    boolean printLog() default true;
    boolean persist() default false;
}
