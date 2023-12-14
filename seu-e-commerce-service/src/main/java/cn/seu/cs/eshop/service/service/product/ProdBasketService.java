package cn.seu.cs.eshop.service.service.product;

import cn.seu.cs.eshop.service.sdk.product.basket.req.ListUserProdBasketResponse;
import cn.seu.cs.eshop.service.sdk.product.basket.req.UpdateUserProdBasketRequest;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
public interface ProdBasketService {
    BaseResponse getUserProdBasketCount(Long userId);
    ListUserProdBasketResponse listUserProdBasket(Long userId);
    BaseResponse updateUserProdBasket(UpdateUserProdBasketRequest request);
    BaseResponse changeProdBasketCount(Long basketId, Integer count);
    BaseResponse batchDeleteBaskets(List<Long> ids);
}
