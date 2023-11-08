package cn.seu.cs.eshop.common.enums;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/8
 */
@Getter
public enum ProdStatusEnum {
    INVALID(0),
    VALID(1),

    ;
    private final int status;

    ProdStatusEnum(int status) {
        this.status = status;
    }
}
