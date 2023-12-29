package cn.seu.cs.eshop.service.rpc.product;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdToCService;
import cn.seu.cs.eshop.service.service.product.ProductToCService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@DubboService(timeout = 5000)
public class EshopProdToCServiceRpc implements EshopProdToCService {
    @Resource
    private ProductToCService productToCService;

    @Override
    @RpcMonitor
    public GetEshopIndexProdsResponse getEshopIndexProds() {
        return productToCService.getEshopIndexProds();
    }

    @Override
    @RpcMonitor
    public ListPageProductResponse listPageStyleProduct(ListPageStyleProductRequest request) {
        return productToCService.listPageStyleProduct(request);
    }

    @Override
    @RpcMonitor
    public ListPageProductResponse listPageUserProd(ListPageUserProdRequest request) {
        return productToCService.listPageUserProd(request);
    }

    @Override
    @RpcMonitor
    public ListPageProductResponse listPageCategoryProd(ListPageCategoryProdRequest request) {
        return productToCService.listPageCategoryProd(request);
    }

    @Override
    @RpcMonitor
    public ListPageProductResponse listPageSearchProd(ListPageSearchProdRequest request) {
        return productToCService.listPageSearchProd(request);
    }

    @Override
    @RpcMonitor
    public GetProdUserInfoResponse getProdUserInfo(Long userId) {
        return productToCService.getProdUserInfo(userId);
    }

    @Override
    @RpcMonitor
    public GetProductInfoResponse getProductInfo(Long prodId, Long userId) {
        return productToCService.getProductInfo(prodId, userId);
    }

    @Override
    public BaseResponse updateFavoriteProdStatus(Long userId, Long prodId, Long favoriteId, Integer action) {
        return productToCService.updateFavoriteProdStatus(userId, prodId, favoriteId, action);
    }
}
