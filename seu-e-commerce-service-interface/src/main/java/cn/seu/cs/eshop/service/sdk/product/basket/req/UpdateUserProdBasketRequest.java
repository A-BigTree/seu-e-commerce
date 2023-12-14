package cn.seu.cs.eshop.service.sdk.product.basket.req;

import cn.seu.cs.eshop.service.sdk.product.basket.dto.EshopProdBasketDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseCrudRequest;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@Data
public class UpdateUserProdBasketRequest implements BaseCrudRequest<EshopProdBasketDTO> {
    private int action;
    private EshopProdBasketDTO data;
}
