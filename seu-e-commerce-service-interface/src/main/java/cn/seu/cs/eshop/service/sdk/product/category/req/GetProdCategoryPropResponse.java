package cn.seu.cs.eshop.service.sdk.product.category.req;

import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdPropDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/18
 */
@Data
public class GetProdCategoryPropResponse implements BaseResponseInterface<List<ProdPropDTO>> {
    private Integer code;
    private String msg;
    private List<ProdPropDTO> data;
}
