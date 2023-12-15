package cn.seu.cs.eshop.service.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@AllArgsConstructor
@Getter
public enum EshopOrderType {
    /**
     * 直接下单
     */
    DIRECT(1),
    /**
     * 购物车下单
     */
    CART(2),
    ;
    final int type;
}
