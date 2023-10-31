package cn.seu.cs.eshop.common.enums;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/31
 */
@Getter
public enum RegisterStateEnum {
    UNDER_REVIEW(0),
    REGISTER_SUCCESS(1),
    REGISTER_ERROR(2),
    ;
    private final Integer state;

    RegisterStateEnum(Integer state) {
        this.state = state;
    }
}
