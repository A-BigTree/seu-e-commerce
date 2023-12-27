package cn.seu.cs.eshop.service.sdk.order.comm.req;

import cn.seu.cs.eshop.service.sdk.order.comm.dto.EshopProdCommDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/27
 */
@Data
public class AddProdCommRequest implements Serializable {
    private EshopProdCommDTO data;
}
