package cn.seu.cs.im.sdk.rpc;

import cn.seu.cs.im.sdk.req.ListPageImMessageRequest;
import cn.seu.cs.im.sdk.req.ListPageImMessageResponse;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
public interface EshopImMessageService {
    /**
     * 获取消息列表
     */
    ListPageImMessageResponse listPageImMessage(ListPageImMessageRequest request);
    BaseResponse countUnreadMessage(Long userId);
}
