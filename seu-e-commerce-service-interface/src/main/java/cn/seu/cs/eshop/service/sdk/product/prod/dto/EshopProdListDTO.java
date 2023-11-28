package cn.seu.cs.eshop.service.sdk.product.prod.dto;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
public class EshopProdListDTO implements PageInterface<EshopProductDTO> {
    private PageDTO page;
    private List<EshopProductDTO> records;
}
