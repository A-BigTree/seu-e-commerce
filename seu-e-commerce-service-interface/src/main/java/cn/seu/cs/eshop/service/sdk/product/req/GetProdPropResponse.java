package cn.seu.cs.eshop.service.sdk.product.req;

import cn.seu.cs.eshop.service.sdk.product.dto.ProdPropDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@Data
public class GetProdPropResponse implements BaseResponseInterface<ProdPropDTO> {
    private Integer code;
    private String msg;
    private ProdPropDTO data;
}
