package cn.seu.cs.eshop.account.sdk.entity.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/18
 */
@Data
public class EshopSessionDTO {
    private String token;

    private UserBaseDTO user;

    private Map<String, String> slots;
}
