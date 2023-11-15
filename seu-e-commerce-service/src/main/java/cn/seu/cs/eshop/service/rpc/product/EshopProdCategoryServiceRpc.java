package cn.seu.cs.eshop.service.rpc.product;

import cn.seu.cs.eshop.common.aop.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.product.req.*;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdCategoryService;
import cn.seu.cs.eshop.service.service.product.ProdCategoryService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@DubboService(timeout = 4000, retries = 0)
public class EshopProdCategoryServiceRpc implements EshopProdCategoryService {
    @Resource
    ProdCategoryService prodCategoryService;

    @Override
    @RpcMonitor
    public BaseResponse updateProdCategory(UpdateProdCategoryRequest request) {
        return prodCategoryService.updateProdCategory(request);
    }

    @Override
    @RpcMonitor
    public ListPageProdCategoryResponse listPageProdCategory(ListPageProdCategoryRequest request) {
        return prodCategoryService.listPageProdCategory(request);
    }

    @Override
    @RpcMonitor
    public GetProdCategoryResponse getProdCategory(Long id) {
        return prodCategoryService.getProdCategory(id);
    }

    @Override
    @RpcMonitor
    public GetAllProdCategoryResponse getAllProdCategory(Long shopId) {
        return prodCategoryService.getAllProdCategory(shopId);
    }

    @Override
    @RpcMonitor
    public BaseResponse updateProdProp(UpdateProdPropRequest request) {
        return null;
    }

    @Override
    @RpcMonitor
    public ListPageProdPropResponse listPageProdProp(ListPageProdPropRequest request) {
        return null;
    }

    @Override
    @RpcMonitor
    public GetProdPropResponse getProdProp(Long id, Long shopId) {
        return null;
    }
}
