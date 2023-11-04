package cn.seu.cs.eshop.account.service;

import cn.seu.cs.eshop.account.sdk.entity.req.*;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import org.springframework.stereotype.Service;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
public interface UserInfoService {
    ListRegisterInfoResponse listRegisterInfo(ListRegisterInfoRequest request);

    GetAccountInfoResponse getAccountInfo(Long id);

    BaseResponse updateRegisterState(UpdateRegisterStateRequest request);
}
