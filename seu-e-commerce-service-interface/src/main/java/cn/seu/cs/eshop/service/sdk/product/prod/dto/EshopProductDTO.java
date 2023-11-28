package cn.seu.cs.eshop.service.sdk.product.prod.dto;

import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdCategoryDTO;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EshopProductDTO implements Serializable {
    private Long id;
    private String prodName;
    private Long shopId;
    private Integer status;
    private Long originPrice;
    private Long price;
    private ProdCategoryDTO category;
    private Integer totalStocks;
    private Integer soldNum;
    private String brief;
    private String content;
    private String pic;
    private List<String> images;
    private Integer deliveryMode;
    private Integer deliveryPrice;
    private String updateTime;
    private String publishTime;
    private String createTime;
    private List<EshopProdSkuDTO> skus;
}
