package cn.seu.cs.eshop.service.sdk.order.order.req;

import cn.seu.cs.eshop.service.sdk.order.order.dto.EshopOrderDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;


/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
public class GetOrderInfoResponse implements BaseResponseInterface<EshopOrderDTO> {
    private EshopOrderDTO data;
    private Integer code;
    private String msg;
}
