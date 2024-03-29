package cs.seu.cs.eshop.common.sdk.enums;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/8
 */
@Getter
public enum EshopStatusEnum {
    DELETED(-1),
    INVALID(0),
    VALID(1),

    ;
    private final int status;

    EshopStatusEnum(int status) {
        this.status = status;
    }
}
