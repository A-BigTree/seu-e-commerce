package cs.seu.cs.eshop.common.sdk.entity.req;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@SuperBuilder
@Data
public class BaseResponse {
    Integer code;
    String msg;
}
