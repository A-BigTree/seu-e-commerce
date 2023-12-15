package cn.seu.cs.eshop.service.sdk.order.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusChangeDTO implements Serializable {
    private Long id;
    private Long orderId;
    private String modifier;
    private String remark;
    private Integer status;
    private Integer oldStatus;
    private String createTime;
}
