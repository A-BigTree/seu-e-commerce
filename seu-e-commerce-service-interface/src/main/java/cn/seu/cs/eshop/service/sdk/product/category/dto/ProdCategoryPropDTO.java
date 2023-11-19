package cn.seu.cs.eshop.service.sdk.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdCategoryPropDTO implements Serializable {
    private Long id;
    private Long categoryId;
    private Long propId;
    private Long shopId;
    private Integer propType;
    private ProdPropDTO prop;
}
