package cn.seu.cs.eshop.account.sdk.entity.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
@Data
public class RegisterUserRequest implements Serializable {
    private String account;
    private String nickname;
    private String verifyCode;
    private String password;
    private Integer roleType;
    private String Ext;
}
