package cn.seu.cs.eshop.service.sdk.product.basket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopProdBasketDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long prodId;
    private String prodName;
    private Long skuId;
    private String skuName;
    private Long price;
    private Long shopId;
    private Integer prodCount;
    private Integer status;
    private String createTime;
}
