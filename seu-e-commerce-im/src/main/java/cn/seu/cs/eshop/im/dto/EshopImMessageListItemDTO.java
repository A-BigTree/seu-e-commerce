package cn.seu.cs.eshop.im.dto;

import cs.seu.cs.eshop.common.sdk.entity.dto.EshopImMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopImMessageListItemDTO implements Serializable {
    private Long toUserId;
    private String headImg;
    private String nickname;
    private EshopImMessageDTO lastMessage;
    private Long unreadCount;
}
