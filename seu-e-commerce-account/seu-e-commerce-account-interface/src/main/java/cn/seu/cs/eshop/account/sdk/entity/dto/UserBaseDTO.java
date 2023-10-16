package cn.seu.cs.eshop.account.sdk.entity.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Builder
public class UserBaseDTO {
    private Long id;
    private String account;
    private String password;
    private String nickname;
}
