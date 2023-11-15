package cn.seu.cs.eshop.service.sdk.product.req;

import cn.seu.cs.eshop.service.sdk.product.dto.ProdPropsListDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@Data
public class ListPageProdPropResponse implements BaseResponseInterface<ProdPropsListDTO> {
    private Integer code;
    private String msg;
    private ProdPropsListDTO data;
}
