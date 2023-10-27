package cn.seu.cs.eshop.account.sdk.entity.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/27
 */
@Data
public class UpdateUserInfoRequest implements Serializable {
    private String image;
    private String nickname;
    private String phoneNumber;
}
