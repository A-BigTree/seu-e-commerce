package cn.seu.cs.eshop.service.sdk.product.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopIndexProdListDTO implements Serializable {
    private Integer style;
    private Long id;
    private String title;
    private List<EshopProductDTO> prods;
}
