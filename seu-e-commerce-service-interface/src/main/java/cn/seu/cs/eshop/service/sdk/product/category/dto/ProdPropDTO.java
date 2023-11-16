package cn.seu.cs.eshop.service.sdk.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdPropDTO implements Serializable {
    private Long id;
    private String propName;
    private Integer propType;
    private Long shopId;
    private Integer selfAdd;
    private String createTime;
    private List<ProdPropValueDTO> value;
}
