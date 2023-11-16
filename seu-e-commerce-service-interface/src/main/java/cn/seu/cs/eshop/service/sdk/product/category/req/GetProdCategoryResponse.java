package cn.seu.cs.eshop.service.sdk.product.category.req;

import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdCategoryDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/8
 */
@Data
public class GetProdCategoryResponse implements BaseResponseInterface<ProdCategoryDTO> {
    private Integer code;
    private String msg;
    private ProdCategoryDTO data;
}
