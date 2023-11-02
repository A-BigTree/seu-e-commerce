package cn.seu.cs.eshop.account.service;

import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoResponse;
import org.springframework.stereotype.Service;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
public interface UserInfoService {
    ListRegisterInfoResponse listRegisterInfo(ListRegisterInfoRequest request);
}
