package cn.seu.cs.eshop.account.sdk.entity.req;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@Data
public class ListRegisterInfoRequest implements Serializable {
    private Long id;
    private String nickname;
    private String email;
    private int registerState;
    private int roleType;
    private PageDTO page;
}
