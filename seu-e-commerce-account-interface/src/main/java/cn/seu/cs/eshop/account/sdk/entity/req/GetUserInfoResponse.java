package cn.seu.cs.eshop.account.sdk.entity.req;

import cn.seu.cs.eshop.account.sdk.entity.dto.EshopSessionDTO;
import cn.seu.cs.eshop.account.sdk.entity.dto.UserInfoDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/24
 */
@Data
public class GetUserInfoResponse implements BaseResponseInterface<UserInfoDTO> {
    private Integer code;
    private String msg;
    private UserInfoDTO data;
}
