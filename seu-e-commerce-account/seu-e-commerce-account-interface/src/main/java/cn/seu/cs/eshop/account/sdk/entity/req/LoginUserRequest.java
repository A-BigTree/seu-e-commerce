package cn.seu.cs.eshop.account.sdk.entity.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
@Data
public class LoginUserRequest implements Serializable {
    private String account;
    private String password;
}
