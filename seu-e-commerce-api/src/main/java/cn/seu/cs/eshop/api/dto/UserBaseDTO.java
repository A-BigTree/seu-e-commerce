package cn.seu.cs.eshop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBaseDTO implements Serializable {
    private Long id;
    private String account;
    private String nickname;
    private String headPic;
    private Integer roleType;
}
