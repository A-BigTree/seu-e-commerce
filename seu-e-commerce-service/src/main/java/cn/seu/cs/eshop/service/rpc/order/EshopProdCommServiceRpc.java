package cn.seu.cs.eshop.service.rpc.order;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.service.sdk.order.comm.req.AddProdCommRequest;
import cn.seu.cs.eshop.service.sdk.order.comm.req.ListPageProdCommRequest;
import cn.seu.cs.eshop.service.sdk.order.comm.req.ListPageProdCommResponse;
import cn.seu.cs.eshop.service.sdk.order.comm.req.ListUserProdCommResponse;
import cn.seu.cs.eshop.service.sdk.order.rpc.EshopProdCommService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/27
 */
public class EshopProdCommServiceRpc implements EshopProdCommService {
    @Override
    @RpcMonitor
    public ListPageProdCommResponse listPageProdComm(ListPageProdCommRequest request) {
        return null;
    }

    @Override
    @RpcMonitor
    public ListUserProdCommResponse listUserProdComm(Long userId) {
        return null;
    }

    @Override
    @RpcMonitor
    public BaseResponse addProdComm(AddProdCommRequest request) {
        return null;
    }
}
