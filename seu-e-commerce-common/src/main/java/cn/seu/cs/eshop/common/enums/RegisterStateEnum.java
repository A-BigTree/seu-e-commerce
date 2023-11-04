package cn.seu.cs.eshop.common.enums;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/31
 */
@Getter
public enum RegisterStateEnum {
    DEFAULT(-1),
    UNDER_REVIEW(0),
    REGISTER_SUCCESS(1),
    REGISTER_ERROR(2),
    BE_DELETED(3)
    ;
    private final int state;

    RegisterStateEnum(Integer state) {
        this.state = state;
    }
}
