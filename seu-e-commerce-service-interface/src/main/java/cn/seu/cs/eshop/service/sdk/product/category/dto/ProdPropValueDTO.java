package cn.seu.cs.eshop.service.sdk.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdPropValueDTO implements Serializable {
    private Long id;
    private String valueName;
    private Long propId;
    private Long shopId;
    private String createTime;
}
