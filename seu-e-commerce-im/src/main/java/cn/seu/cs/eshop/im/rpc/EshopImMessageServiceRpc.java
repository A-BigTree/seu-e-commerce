package cn.seu.cs.eshop.im.rpc;

import cn.seu.cs.eshop.common.annotation.RpcMonitor;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.im.convert.EshopImConvert;
import cn.seu.cs.eshop.im.dao.EshopImMessageDao;
import cn.seu.cs.eshop.im.db.EshopImMessageDO;
import cn.seu.cs.im.sdk.dto.ImMessagePageDTO;
import cn.seu.cs.im.sdk.req.ListPageImMessageRequest;
import cn.seu.cs.im.sdk.req.ListPageImMessageResponse;
import cn.seu.cs.im.sdk.rpc.EshopImMessageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@DubboService(timeout = 5000)
public class EshopImMessageServiceRpc implements EshopImMessageService {
    @Resource
    private EshopImMessageDao eshopImMessageDao;

    @Override
    @RpcMonitor
    public ListPageImMessageResponse listPageImMessage(ListPageImMessageRequest request) {
        IPage<EshopImMessageDO> messages =
                eshopImMessageDao.selectMessagePageByUserId(request.getFromUserId(), request.getToUserId(), request.getPage());
        ImMessagePageDTO data = MysqlUtils.buildPageData(ImMessagePageDTO.class, messages, EshopImConvert::convertToEshopImMessageDTO);
        return buildSuccessResponse(ListPageImMessageResponse.class, data);
    }

    @Override
    @RpcMonitor
    public BaseResponse countUnreadMessage(Long userId) {
        long count = eshopImMessageDao.countUnreadMessage(userId);
        return buildSuccessResponse(BaseResponse.class, String.valueOf(count));
    }
}
