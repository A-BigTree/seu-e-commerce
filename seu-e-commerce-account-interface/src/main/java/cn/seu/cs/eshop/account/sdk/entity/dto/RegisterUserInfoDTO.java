package cn.seu.cs.eshop.account.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserInfoDTO implements Serializable {
    private Long id;
    private String nickname;
    private String account;
    private String phoneNumber;
    private String image;
    private Integer roleType;
    private Integer registerState;
    private String createTime;
}
