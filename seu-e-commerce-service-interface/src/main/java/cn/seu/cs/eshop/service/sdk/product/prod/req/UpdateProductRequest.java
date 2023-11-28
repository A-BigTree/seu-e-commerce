package cn.seu.cs.eshop.service.sdk.product.prod.req;

import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProductDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseCrudRequest;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
public class UpdateProductRequest implements BaseCrudRequest<EshopProductDTO> {
    private Integer action;
    private EshopProductDTO data;
}
