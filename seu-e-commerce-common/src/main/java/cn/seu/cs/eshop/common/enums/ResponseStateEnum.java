package cn.seu.cs.eshop.common.enums;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Getter
public enum ResponseStateEnum {
    // 成功
    OK(200, "Ok"),
    // 服务器内部错误
    ERROR(500, "Server error"),
    // 需要登录认证
    AUTHORIZATION(501, "未登录，请先登录"),
    // 登录过期
    AUTHORIZATION_EXPIRATION(502, "登录已过期，请重新登录"),
    // 用户操作错误
    OPERATION_ERROR(503, "Operation Error"),
    // 权限错误
    PRIVILEGES_ERROR(504, "无权限进行操作"),

    // 其他错误
    OTHER_ERROR(600, "Other Error"),
    ;
    private final Integer code;
    private final String msg;

    ResponseStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
