package cn.seu.cs.eshop.service.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@AllArgsConstructor
@Getter
public enum EshopOrderCloseTypeEnum {
    /**
     * 用户取消
     */
    USER_CANCEL(1, "用户取消"),
    /**
     * 超时取消
     */
    TIMEOUT_CANCEL(2, "订单超时"),
    /**
     * 商家取消
     */
    MERCHANT_CANCEL(3, "商家取消"),
    ;

    final int closeType;
    final String closeTypeDesc;

    private static final Map<Integer, EshopOrderCloseTypeEnum> MAP = new HashMap<>();

    static {
        for (EshopOrderCloseTypeEnum value : EshopOrderCloseTypeEnum.values()) {
            MAP.put(value.getCloseType(), value);
        }
    }

    public static EshopOrderCloseTypeEnum getByCloseType(int closeType) {
        return MAP.get(closeType);
    }
}
