package cn.seu.cs.eshop.service.sdk.order.rpc;

import cn.seu.cs.eshop.service.sdk.order.comm.req.AddProdCommRequest;
import cn.seu.cs.eshop.service.sdk.order.comm.req.ListPageProdCommRequest;
import cn.seu.cs.eshop.service.sdk.order.comm.req.ListPageProdCommResponse;
import cn.seu.cs.eshop.service.sdk.order.comm.req.ListUserProdCommResponse;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/16
 */
public interface EshopProdCommService {
    ListPageProdCommResponse listPageProdComm(ListPageProdCommRequest request);
    ListUserProdCommResponse listUserProdComm(Long userId);
    BaseResponse addProdComm(AddProdCommRequest request);
}
