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
    private String modifier;
    private Long orderId;
    private String orderNumber;
    private Integer status;
    private String param1;
    private String param2;
    private String param3;
}
