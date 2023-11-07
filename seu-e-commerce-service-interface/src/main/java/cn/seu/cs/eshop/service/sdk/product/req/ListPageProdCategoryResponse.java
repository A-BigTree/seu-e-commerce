package cn.seu.cs.eshop.service.sdk.product.req;

import cn.seu.cs.eshop.service.sdk.product.dto.ProdCategoryListDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@Data
public class ListPageProdCategoryResponse implements BaseResponseInterface<ProdCategoryListDTO> {
    private Integer code;
    private String msg;
    private ProdCategoryListDTO data;
}
