package cn.seu.cs.eshop.service.sdk.product.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdCategoryDTO implements Serializable {
    private Long id;
    private Long shopId;
    private Long parentId;
    private String categoryName;
    private Integer status;
    private Integer level;
    private String createTime;
    private List<ProdCategoryDTO> children;
}
