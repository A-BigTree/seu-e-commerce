package cn.seu.cs.eshop.service.sdk.order.order.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
public class OrderInitIdsDTO implements Serializable {
    private Long id;
    private Long prodId;
    private Long skuId;
    private Integer prodCount;
}
