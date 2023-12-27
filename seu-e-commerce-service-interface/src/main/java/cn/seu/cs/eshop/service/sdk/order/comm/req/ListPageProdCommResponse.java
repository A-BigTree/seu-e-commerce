package cn.seu.cs.eshop.service.sdk.order.comm.req;

import cn.seu.cs.eshop.service.sdk.order.comm.dto.EshopProdCommPageDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/27
 */
@Data
public class ListPageProdCommResponse implements BaseResponseInterface<EshopProdCommPageDTO> {
    private EshopProdCommPageDTO data;
    private Integer code;
    private String msg;
}
