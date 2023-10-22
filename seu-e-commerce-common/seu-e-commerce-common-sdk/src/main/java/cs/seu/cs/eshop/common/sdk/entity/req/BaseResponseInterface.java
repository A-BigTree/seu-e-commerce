package cs.seu.cs.eshop.common.sdk.entity.req;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
public interface BaseResponseInterface<T> extends Serializable {
    void setCode(Integer code);

    void setMsg(String msg);

    void setData(T data);
}
