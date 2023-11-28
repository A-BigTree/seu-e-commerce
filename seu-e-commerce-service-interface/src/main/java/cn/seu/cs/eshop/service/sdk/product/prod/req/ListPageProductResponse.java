package cn.seu.cs.eshop.service.sdk.product.prod.req;

import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdListDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
public class ListPageProductResponse implements BaseResponseInterface<EshopProdListDTO> {
    private Integer code;
    private String msg;
    private EshopProdListDTO data;
}
