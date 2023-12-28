package cn.seu.cs.eshop.service.rpc.order;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.order.comm.req.*;
import cn.seu.cs.eshop.service.sdk.order.rpc.EshopProdCommService;
import cn.seu.cs.eshop.service.service.order.ProdCommService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/27
 */
@DubboService(timeout = 5000, retries = 0)
public class EshopProdCommServiceRpc implements EshopProdCommService {
    @Resource
    private ProdCommService prodCommService;

    @Override
    @RpcMonitor
    public ListPageProdCommResponse listPageProdComm(ListPageProdCommRequest request) {
        return prodCommService.listPageProdComm(request);
    }

    @Override
    @RpcMonitor
    public ListUserProdCommResponse listUserProdComm(Long userId) {
        return prodCommService.listUserProdComm(userId);
    }

    @Override
    @RpcMonitor
    public BaseResponse addProdComm(AddProdCommRequest request) {
        return prodCommService.addProdComm(request);
    }

    @Override
    @RpcMonitor
    public GetProdCommCountResponse getProdCommCount(Long prodId) {
        return prodCommService.getProdCommCount(prodId);
    }
}
