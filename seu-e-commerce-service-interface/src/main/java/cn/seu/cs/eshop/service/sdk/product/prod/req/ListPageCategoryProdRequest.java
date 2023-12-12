package cn.seu.cs.eshop.service.sdk.product.prod.req;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Data
public class ListPageCategoryProdRequest implements Serializable {
    private Long categoryId;
    private PageDTO page;
}
