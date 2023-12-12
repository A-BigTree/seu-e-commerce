package cn.seu.cs.eshop.service.sdk.order.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopOrderAddressDTO implements Serializable{
    private Long id;
    private Long userId;
    private String receiver;
    private String mobile;
    private String address;
    private Integer defaultAddress;
    private EshopOrderAreaDTO area;
    private String createTime;
}
