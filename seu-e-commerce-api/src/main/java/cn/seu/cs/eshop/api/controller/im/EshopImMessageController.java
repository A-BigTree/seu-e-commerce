package cn.seu.cs.eshop.api.controller.im;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.im.sdk.req.ListPageImMessageRequest;
import cn.seu.cs.im.sdk.req.ListPageImMessageResponse;
import cn.seu.cs.im.sdk.rpc.EshopImMessageService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@RestController
@RequestMapping("/im/message")
public class EshopImMessageController {
    @DubboReference(timeout = 5000)
    private EshopImMessageService eshopImMessageService;

    @ApiMonitor
    @CrossOrigin
    @PostMapping("/page/list")
    public ListPageImMessageResponse listPageImMessage(@RequestBody ListPageImMessageRequest request,
                                                       @AuthorUserInfo Long userId) {
        request.setFromUserId(userId);
        return eshopImMessageService.listPageImMessage(request);
    }

    @ApiMonitor
    @CrossOrigin
    @GetMapping("/unread/count")
    public BaseResponse countUnreadMessage(@AuthorUserInfo Long userId) {
        return eshopImMessageService.countUnreadMessage(userId);
    }
}
