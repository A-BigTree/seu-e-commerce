package cn.seu.cs.eshop.service.sdk.order.comm.dto;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/27
 */
@Data
public class EshopProdCommPageDTO implements PageInterface<EshopProdCommDTO> {
    private PageDTO page;
    private List<EshopProdCommDTO> records;
}
