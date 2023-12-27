package cn.seu.cs.eshop.service.sdk.order.comm.req;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/27
 */
@Data
public class ListPageProdCommRequest implements Serializable {
    private PageDTO page;
    private Long prodId;
}
