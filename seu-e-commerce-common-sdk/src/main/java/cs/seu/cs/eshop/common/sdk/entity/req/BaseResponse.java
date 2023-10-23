package cs.seu.cs.eshop.common.sdk.entity.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Data
public class BaseResponse implements BaseResponseInterface<String> {
    private Integer code;
    private String msg;
    private String data;
}
