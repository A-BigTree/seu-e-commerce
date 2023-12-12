package cn.seu.cs.eshop.service.sdk.product.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopProdUserInfoDTO implements Serializable {
    private Long favoriteCount;
    private Long historyCount;
    private Long orderCount;
    // TODO: 分类订单数量
}
