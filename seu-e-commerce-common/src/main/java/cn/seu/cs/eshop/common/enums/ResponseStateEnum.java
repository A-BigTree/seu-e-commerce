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
    ERROR(500, "Server error")

    ;
    private final Integer code;
    private final String msg;

    ResponseStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
