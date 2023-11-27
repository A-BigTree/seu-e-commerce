package cs.seu.cs.eshop.common.sdk.enums;

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
    private final int state;

    DeletedStateEnum(Integer state) {
        this.state = state;
    }
}
