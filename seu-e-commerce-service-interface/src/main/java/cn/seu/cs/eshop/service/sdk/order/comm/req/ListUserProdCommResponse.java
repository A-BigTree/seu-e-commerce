package cn.seu.cs.eshop.service.sdk.order.comm.req;

import cn.seu.cs.eshop.service.sdk.order.order.dto.EshopOrderItemDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/27
 */
@Data
public class ListUserProdCommResponse implements BaseResponseInterface<List<EshopOrderItemDTO>> {
    private Integer code;
    private String msg;
    private List<EshopOrderItemDTO> data;
}
