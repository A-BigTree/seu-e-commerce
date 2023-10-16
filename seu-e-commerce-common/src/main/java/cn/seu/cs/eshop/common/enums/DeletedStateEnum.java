package cn.seu.cs.eshop.common.enums;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Getter
public enum DeletedStateEnum {
    UN_KNOW(-1),

    UNDELETED(0),

    DELETED(1),

    ;
    private final Integer state;

    DeletedStateEnum(Integer state) {
        this.state = state;
    }
}
