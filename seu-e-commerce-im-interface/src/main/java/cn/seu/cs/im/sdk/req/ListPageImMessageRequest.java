package cn.seu.cs.im.sdk.req;

import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@Data
public class ListPageImMessageRequest implements Serializable {
    private Long fromUserId;
    private Long toUserId;
    private PageDTO page;
}
