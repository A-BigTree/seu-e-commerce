package cn.seu.cs.eshop.service.sdk.order.order.dto;

import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopOrderDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long shopId;
    private EshopOrderAddressDTO address;
    private String prodName;
    private String orderNumber;
    private String remarks;
    private Integer prodCount;
    private Integer status;
    private String deliveryId;
    private Long deliveryCost;
    private Integer orderType;
    private Integer closeType;
    private Long total;
    private Integer payType;
    private String payTime;
    private String deliveryTime;
    private String completeTime;
    private String cancelTime;
    private String updateTime;
    private String createTime;
    private List<EshopProdOrderDTO> orderItems;
}
