package cn.seu.cs.eshop.service.sdk.order.order.req;

import cn.seu.cs.eshop.service.sdk.order.order.dto.OrderStatusChangeDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
public class ListOrderReviewResponse implements BaseResponseInterface<List<OrderStatusChangeDTO>> {
    private List<OrderStatusChangeDTO> data;
    private Integer code;
    private String msg;
}
