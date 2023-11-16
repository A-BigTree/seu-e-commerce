package cn.seu.cs.eshop.service.sdk.product.category.req;

import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdCategoryDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseCrudRequest;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@Data
public class UpdateProdCategoryRequest implements BaseCrudRequest<ProdCategoryDTO> {
    private int action;
    private ProdCategoryDTO data;
}
