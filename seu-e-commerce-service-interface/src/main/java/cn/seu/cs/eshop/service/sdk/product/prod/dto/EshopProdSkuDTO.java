package cn.seu.cs.eshop.service.sdk.product.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopProdSkuDTO implements Serializable {
    private Long id;
    private Long prodId;
    private List<EshopProdSkuPropDTO> properties;
    private String skuName;
    private String skuCode;
    private String pic;
    private Long originPrice;
    private Long price;
    private Integer stocks;
    private String createTime;
}
