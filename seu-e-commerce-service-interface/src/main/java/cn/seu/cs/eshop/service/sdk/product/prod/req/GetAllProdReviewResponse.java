package cn.seu.cs.eshop.service.sdk.product.prod.req;

import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdReviewDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Data
public class GetAllProdReviewResponse implements BaseResponseInterface<List<EshopProdReviewDTO>> {
    private Integer code;
    private String msg;
    private List<EshopProdReviewDTO> data;
}
