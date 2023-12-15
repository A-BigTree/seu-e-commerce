package cn.seu.cs.eshop.service.sdk.order.order.req;

import cn.seu.cs.eshop.service.sdk.order.order.dto.OrderInitIdsDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
public class InitOrderListRequest implements Serializable {
    private List<OrderInitIdsDTO> orderIds;
}
