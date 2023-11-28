package cn.seu.cs.eshop.service.rpc.product;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdToBService;
import cn.seu.cs.eshop.service.service.product.ProductToBService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@DubboService(timeout = 4000, retries = 0)
public class EshopProductToBServiceRpc implements EshopProdToBService {
    @Resource
    ProductToBService productToBService;

    @Override
    @RpcMonitor
    public ListPageProductResponse listPageProduct(ListPageProductRequest request) {
        return productToBService.listPageProduct(request);
    }

    @Override
    @RpcMonitor
    public BaseResponse updateProduct(UpdateProductRequest request) {
        return productToBService.updateProduct(request);
    }

    @Override
    @RpcMonitor
    public GetAllProdReviewResponse getAllProdReview(Long prodId) {
        return productToBService.getAllProdReview(prodId);
    }

    @Override
    @RpcMonitor
    public BaseResponse updateProdStatus(UpdateProdStatusRequest request) {
        return productToBService.updateProdStatus(request);
    }

    @Override
    @RpcMonitor
    public GetProductInfoResponse getProductInfo(Long prodId) {
        return productToBService.getProductInfo(prodId);
    }
}
