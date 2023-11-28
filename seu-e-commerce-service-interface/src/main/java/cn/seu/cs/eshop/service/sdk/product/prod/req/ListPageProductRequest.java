package cn.seu.cs.eshop.service.sdk.product.prod.req;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
public class ListPageProductRequest implements Serializable {
    private Long shopId;
    private String prodName;
    private Integer status;
    private Long categoryId;
    private Integer deliveryMode;
    private PageDTO page;
}
