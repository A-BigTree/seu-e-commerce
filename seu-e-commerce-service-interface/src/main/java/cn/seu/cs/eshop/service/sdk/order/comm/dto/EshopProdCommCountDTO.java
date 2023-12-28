package cn.seu.cs.eshop.service.sdk.order.comm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopProdCommCountDTO implements Serializable {
    /**
     * 好评数
     */
    private long goodCount;
    /**
     * 中评数
     */
    private long mediumCount;
    /**
     * 差评数
     */
    private long badCount;
}
