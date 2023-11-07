package cs.seu.cs.eshop.common.sdk.entity.req;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
public interface BaseCrudRequest<DTO> extends Serializable {
    int getAction();
    DTO getData();
}
