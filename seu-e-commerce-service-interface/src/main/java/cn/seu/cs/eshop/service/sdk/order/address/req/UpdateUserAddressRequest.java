package cn.seu.cs.eshop.service.sdk.order.address.req;

import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAddressDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseCrudRequest;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Data
public class UpdateUserAddressRequest implements BaseCrudRequest<EshopOrderAddressDTO> {
    private int action;
    private EshopOrderAddressDTO data;
}
