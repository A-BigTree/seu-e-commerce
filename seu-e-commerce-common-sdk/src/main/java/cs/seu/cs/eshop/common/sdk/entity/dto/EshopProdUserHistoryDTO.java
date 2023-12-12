package cs.seu.cs.eshop.common.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopProdUserHistoryDTO implements Serializable {
    private Long userId;
    private Long prodId;
    private Long createTime;
}
