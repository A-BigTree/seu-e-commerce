package cn.seu.cs.eshop.service.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Getter
@AllArgsConstructor
public enum ProdUserInfoTypeEnum {
    FAVORITE_PROD(1),
    HISTORY_PROD(2),

    ;
    final int type;
}
