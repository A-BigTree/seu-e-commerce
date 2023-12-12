package cn.seu.cs.eshop.service.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Getter
@AllArgsConstructor
public enum ProdSearchTypeEnum {
    DEFAULT(0, "", ""),
    SOLD_DESC(1, "soldNum", "desc"),
    PRICE_ASC(2, "price", "asc"),

    ;


    private static final Map<Integer, ProdSearchTypeEnum> MAPPER = new HashMap<>();

    static {
        for (ProdSearchTypeEnum value : ProdSearchTypeEnum.values()) {
            MAPPER.put(value.type, value);
        }
    }

    final int type;
    final String orderBy;
    final String order;

    public static ProdSearchTypeEnum getSearchType(int type) {
        return MAPPER.getOrDefault(type, DEFAULT);
    }
}
