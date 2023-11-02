package cn.seu.cs.eshop.account.sdk.entity.req;

import cn.seu.cs.eshop.account.sdk.entity.dto.RegisterUserListDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@Data
public class ListRegisterInfoResponse implements BaseResponseInterface<RegisterUserListDTO> {
    private Integer code;
    private String msg;
    private RegisterUserListDTO data;
}
