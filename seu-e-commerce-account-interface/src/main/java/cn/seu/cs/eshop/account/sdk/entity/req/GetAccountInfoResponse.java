package cn.seu.cs.eshop.account.sdk.entity.req;

import cn.seu.cs.eshop.account.sdk.entity.dto.AccountInfoDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@Data
public class GetAccountInfoResponse implements BaseResponseInterface<AccountInfoDTO> {
    private Integer code;
    private String msg;
    private AccountInfoDTO data;
}
