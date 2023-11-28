package cn.seu.cs.eshop.service.rpc.product;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.product.prod.req.*;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdToBService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@DubboService(timeout = 4000, retries = 0)
public class EshopProductToBServiceRpc implements EshopProdToBService {
    @Override
    @RpcMonitor
    public ListPageProductResponse listPageProduct(ListPageProductRequest request) {
        return null;
    }

    @Override
    @RpcMonitor
    public BaseResponse updateProduct(UpdateProductRequest request) {
        return null;
    }

    @Override
    @RpcMonitor
    public GetAllProdReviewResponse getAllProdReview(Long prodId) {
        return null;
    }

    @Override
    public BaseResponse updateProdStatus(UpdateProdStatusRequest request) {
        return null;
    }
}
