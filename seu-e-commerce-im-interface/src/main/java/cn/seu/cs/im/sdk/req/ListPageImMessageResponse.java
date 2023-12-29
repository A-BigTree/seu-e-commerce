package cn.seu.cs.im.sdk.req;

import cn.seu.cs.im.sdk.dto.ImMessagePageDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@Data
public class ListPageImMessageResponse implements BaseResponseInterface<ImMessagePageDTO> {
    private ImMessagePageDTO data;
    private Integer code;
    private String msg;
}
