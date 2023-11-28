package cn.seu.cs.eshop.service.sdk.product.prod.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
public class UpdateProdStatusRequest implements Serializable {
    private Long prodId;
    private String modifier;
    private String status;
    private String remark;
}
