package cn.seu.cs.eshop.service.sdk.product.category.dto;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@Data
public class ProdCategoryListDTO implements PageInterface<ProdCategoryDTO> {
    List<ProdCategoryDTO> records;
    PageDTO page;
}
