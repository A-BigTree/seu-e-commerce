package cn.seu.cs.eshop.service.sdk.product.basket.req;

import cn.seu.cs.eshop.service.sdk.order.order.dto.EshopOrderItemDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseCrudRequest;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@Data
public class UpdateUserProdBasketRequest implements BaseCrudRequest<EshopOrderItemDTO> {
    private int action;
    private EshopOrderItemDTO data;
}
