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
public enum EshopOrderPayTypeEnum {
    /**
     * 微信支付
     */
    WECHAT(1, "微信支付"),
    /**
     * 支付宝支付
     */
    ALIPAY(2, "支付宝支付"),
    /**
     * 银联支付
     */
    UNION_PAY(3, "银联支付"),
    /**
     * 余额支付
     */
    BALANCE(4, "余额支付"),
    ;

    private static final Map<Integer, EshopOrderPayTypeEnum> MAP = new HashMap<>();

    static {
        for (EshopOrderPayTypeEnum value : EshopOrderPayTypeEnum.values()) {
            MAP.put(value.getPayType(), value);
        }
    }

    final int payType;
    final String payTypeDesc;

    public static EshopOrderPayTypeEnum getByPayType(int payType) {
        return MAP.get(payType);
    }
}
