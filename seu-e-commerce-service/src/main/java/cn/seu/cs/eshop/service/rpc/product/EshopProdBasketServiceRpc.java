package cn.seu.cs.eshop.service.rpc.product;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.product.basket.req.ListUserProdBasketResponse;
import cn.seu.cs.eshop.service.sdk.product.basket.req.UpdateUserProdBasketRequest;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdBasketService;
import cn.seu.cs.eshop.service.service.product.ProdBasketService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
@DubboService(timeout = 5000, retries = 0)
public class EshopProdBasketServiceRpc implements EshopProdBasketService {
    @Resource
    private ProdBasketService prodBasketService;

    @Override
    @RpcMonitor
    public BaseResponse getUserProdBasketCount(Long userId) {
        return prodBasketService.getUserProdBasketCount(userId);
    }

    @Override
    @RpcMonitor
    public ListUserProdBasketResponse listUserProdBasket(Long userId) {
        return prodBasketService.listUserProdBasket(userId);
    }

    @Override
    @RpcMonitor
    public BaseResponse updateUserProdBasket(UpdateUserProdBasketRequest request) {
        return prodBasketService.updateUserProdBasket(request);
    }

    @Override
    @RpcMonitor
    public BaseResponse changeProdBasketCount(Long basketId, Integer count) {
        return prodBasketService.changeProdBasketCount(basketId, count);
    }

    @Override
    @RpcMonitor
    public BaseResponse batchDeleteBaskets(List<Long> ids) {
        return prodBasketService.batchDeleteBaskets(ids);
    }
}
