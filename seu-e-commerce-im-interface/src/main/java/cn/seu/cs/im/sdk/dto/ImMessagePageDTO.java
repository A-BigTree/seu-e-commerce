package cn.seu.cs.im.sdk.dto;

import cs.seu.cs.eshop.common.sdk.entity.dto.EshopImMessageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@Data
public class ImMessagePageDTO implements PageInterface<EshopImMessageDTO> {
    PageDTO page;
    List<EshopImMessageDTO> records;
}
