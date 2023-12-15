package cn.seu.cs.eshop.service.sdk.order.order.req;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
public class ListPageOrderRequest implements Serializable {
    private PageDTO page;
    private String orderNumber;
    private Long userId;
    private Long shopId;
    private Integer roleType;
    private Integer status;
    private Integer closeType;
    private Integer payType;
}
