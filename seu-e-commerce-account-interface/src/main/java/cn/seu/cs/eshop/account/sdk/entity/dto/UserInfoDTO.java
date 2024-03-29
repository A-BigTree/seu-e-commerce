package cn.seu.cs.eshop.account.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO implements Serializable {
    private Long id;
    private String account;
    private String image;
    private String nickname;
    private Integer roleType;
    private String phoneNumber;
}
