package cn.seu.cs.eshop.service.sdk.product.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopProdSkuPropDTO implements Serializable {
    private String prop;
    private String value;
}
