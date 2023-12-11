package cn.seu.cs.eshop.service.sdk.product.prod.req;

import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopIndexProdListDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@Data
public class GetEshopIndexProdsResponse implements BaseResponseInterface<EshopIndexProdListDTO> {
    private EshopIndexProdListDTO data;
    private Integer code;
    private String msg;
}
