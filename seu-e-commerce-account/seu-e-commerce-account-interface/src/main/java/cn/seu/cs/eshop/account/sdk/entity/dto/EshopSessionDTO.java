package cn.seu.cs.eshop.account.sdk.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/18
 */
@Data
@Builder
public class EshopSessionDTO {
    private Long sessionId;

    private UserBaseDTO user;

    private Map<String, String> slots;
}
