package cn.seu.cs.eshop.service.sdk.product.req;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
public class ListPageProdPropRequest implements Serializable {
    private Long shopId;
    private PageDTO page;
}
