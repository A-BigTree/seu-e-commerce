package cn.seu.cs.eshop.service.sdk.order.order.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
public class ChangeOrderStatusRequest implements Serializable {
    private Long userId;
    private Long orderId;
    private Integer status;
    private String params;
}
