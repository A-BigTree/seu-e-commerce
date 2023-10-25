package cn.seu.cs.eshop.api.annotation;

import java.lang.annotation.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/24
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorUserInfo {
}
