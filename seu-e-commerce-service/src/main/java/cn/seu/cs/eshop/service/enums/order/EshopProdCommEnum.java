package cn.seu.cs.eshop.service.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/28
 */
@AllArgsConstructor
@Getter
public enum EshopProdCommEnum {
    /**
     * 好评
     */
    GOOD(1),
    /**
     * 中评
     */
    MEDIUM(2),
    /**
     * 差评
     */
    BAD(3)
    ;
    final int type;
}
