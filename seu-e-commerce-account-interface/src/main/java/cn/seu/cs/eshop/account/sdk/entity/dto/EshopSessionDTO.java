package cn.seu.cs.eshop.account.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EshopSessionDTO implements Serializable {
    private Long id;
    private String token;
    private UserBaseDTO user;
    private Map<String, String> slots;
}
