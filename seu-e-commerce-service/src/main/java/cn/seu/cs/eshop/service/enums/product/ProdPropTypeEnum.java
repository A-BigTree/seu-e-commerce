package cn.seu.cs.eshop.service.enums.product;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/16
 */
@Getter
public enum ProdPropTypeEnum {
    // 全部 用于搜索
    DEFAULT(-1),
    // 规格属性
    SPECIFICATION_PROP(1),
    // 参数属性
    PARAMETER_PROP(2),
    ;
    final int type;

    ProdPropTypeEnum(int type) {
        this.type = type;
    }
}
