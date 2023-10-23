package cs.seu.cs.eshop.common.sdk.entity.req;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/20
 */
@SuperBuilder
@Data
public class BaseRequest implements Serializable {
    Long requestId;
}
