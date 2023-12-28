package cn.seu.cs.eshop.service.service.order;

import cn.seu.cs.eshop.service.sdk.order.comm.req.*;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/28
 */
public interface ProdCommService {
    ListPageProdCommResponse listPageProdComm(ListPageProdCommRequest request);
    ListUserProdCommResponse listUserProdComm(Long userId);
    BaseResponse addProdComm(AddProdCommRequest request);
    GetProdCommCountResponse getProdCommCount(Long prodId);
}
