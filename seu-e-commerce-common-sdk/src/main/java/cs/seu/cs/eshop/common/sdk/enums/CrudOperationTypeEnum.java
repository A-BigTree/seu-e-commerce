package cs.seu.cs.eshop.common.sdk.enums;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@Getter
public enum CrudOperationTypeEnum {
    INSERT(1),
    DELETE(2),
    UPDATE(3),
    ;
    private final int type;

    CrudOperationTypeEnum(int type) {
        this.type = type;
    }
}
