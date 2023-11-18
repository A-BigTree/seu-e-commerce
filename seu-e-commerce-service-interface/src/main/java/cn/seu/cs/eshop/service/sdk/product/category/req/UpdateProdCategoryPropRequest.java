package cn.seu.cs.eshop.service.sdk.product.category.req;

import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdCategoryPropDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/18
 */
@Data
public class UpdateProdCategoryPropRequest implements Serializable {
    private Long categoryId;
    private Integer propType;
    private Long shopId;
    private List<ProdCategoryPropDTO> props;
}
