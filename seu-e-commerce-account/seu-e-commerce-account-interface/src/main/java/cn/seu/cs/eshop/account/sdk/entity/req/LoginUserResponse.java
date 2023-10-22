package cn.seu.cs.eshop.account.sdk.entity.req;

import cn.seu.cs.eshop.account.sdk.entity.dto.EshopSessionDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
@Data
public class LoginUserResponse implements BaseResponseInterface<EshopSessionDTO> {
    private Integer code;
    private String msg;
    private EshopSessionDTO data;
}
