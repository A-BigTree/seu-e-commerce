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
public class EshopOrderAreaDTO implements Serializable {
    private Long areaId;
    private EshopAreaDTO province;
    private EshopAreaDTO city;
    private EshopAreaDTO area;
}
