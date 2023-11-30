package cn.seu.cs.eshop.service.sdk.product.prod.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
public class EshopProdReviewDTO implements Serializable {
    private String modifier;
    private String remark;
    private Integer status;
    private Integer oldStatus;
    private String createTime;
}
