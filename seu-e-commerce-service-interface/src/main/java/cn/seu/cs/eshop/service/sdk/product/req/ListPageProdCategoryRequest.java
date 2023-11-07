package cn.seu.cs.eshop.service.sdk.product.req;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@Data
public class ListPageProdCategoryRequest implements Serializable {
    private Long parentId;
    private Long shopId;
    private PageDTO page;
}
