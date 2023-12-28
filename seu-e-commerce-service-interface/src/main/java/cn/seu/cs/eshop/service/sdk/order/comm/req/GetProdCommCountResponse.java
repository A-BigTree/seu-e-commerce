package cn.seu.cs.eshop.service.sdk.order.comm.req;

import cn.seu.cs.eshop.service.sdk.order.comm.dto.EshopProdCommCountDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/28
 */
@Data
public class GetProdCommCountResponse implements BaseResponseInterface<EshopProdCommCountDTO> {
    private EshopProdCommCountDTO data;
    private Integer code;
    private String msg;
}
