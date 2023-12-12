package cn.seu.cs.eshop.service.sdk.order.address.req;

import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAddressDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Data
public class ListUserAddressResponse implements BaseResponseInterface<List<EshopOrderAddressDTO>> {
    private List<EshopOrderAddressDTO> data;
    private Integer code;
    private String msg;
}
