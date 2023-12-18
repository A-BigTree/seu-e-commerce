package cs.seu.cs.eshop.common.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopImSessionDTO implements Serializable {
    private Long id;
    private Integer sendType;
    private Long fromUserId;
    private Long toUserId;
    private EshopImMessageDTO message;
}
