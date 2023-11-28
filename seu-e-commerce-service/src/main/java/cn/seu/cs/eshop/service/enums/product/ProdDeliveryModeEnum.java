package cn.seu.cs.eshop.service.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Getter
@AllArgsConstructor
public enum ProdDeliveryModeEnum {
    FREE_DELIVERY(0),
    NO_FREE_DELIVERY(1),
    JD_DELIVERY(2)

    ;
    final int mode;
}
