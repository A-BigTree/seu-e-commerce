package cn.seu.cs.eshop.service.sdk.order.order.dto;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
public class EshopOrderPageDTO implements PageInterface<EshopOrderDTO> {
    private PageDTO page;
    private List<EshopOrderDTO> records;
}
