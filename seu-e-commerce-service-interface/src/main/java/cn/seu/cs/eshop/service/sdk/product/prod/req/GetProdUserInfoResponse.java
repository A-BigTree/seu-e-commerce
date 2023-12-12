package cn.seu.cs.eshop.service.sdk.product.prod.req;

import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdUserInfoDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Data
public class GetProdUserInfoResponse implements BaseResponseInterface<EshopProdUserInfoDTO> {
    private EshopProdUserInfoDTO data;
    private Integer code;
    private String msg;
}
