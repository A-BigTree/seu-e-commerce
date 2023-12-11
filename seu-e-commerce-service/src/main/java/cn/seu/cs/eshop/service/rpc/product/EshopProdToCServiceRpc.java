package cn.seu.cs.eshop.service.rpc.product;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.product.prod.req.GetEshopIndexProdsResponse;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdToCService;
import cn.seu.cs.eshop.service.service.product.ProductToCService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@DubboService
public class EshopProdToCServiceRpc implements EshopProdToCService {
    @Resource
    private ProductToCService productToCService;

    @Override
    @RpcMonitor
    public GetEshopIndexProdsResponse getEshopIndexProds() {
        return productToCService.getEshopIndexProds();
    }
}
