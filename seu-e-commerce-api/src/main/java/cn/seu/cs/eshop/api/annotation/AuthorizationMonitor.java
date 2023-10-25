package cn.seu.cs.eshop.api.annotation;

import cn.seu.cs.eshop.common.enums.UserRoleEnum;

import java.lang.annotation.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorizationMonitor {
    UserRoleEnum roleType() default UserRoleEnum.CUSTOMER;
}
