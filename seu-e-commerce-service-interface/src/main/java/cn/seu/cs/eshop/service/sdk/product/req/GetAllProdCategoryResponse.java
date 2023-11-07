package cn.seu.cs.eshop.service.sdk.product.req;

import cn.seu.cs.eshop.service.sdk.product.dto.ProdCategoryDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@Data
public class GetAllProdCategoryResponse implements BaseResponseInterface<List<ProdCategoryDTO>> {
    private Integer code;
    private String msg;
    private List<ProdCategoryDTO> data;
}
