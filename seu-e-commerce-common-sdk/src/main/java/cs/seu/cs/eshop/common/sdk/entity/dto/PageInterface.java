package cs.seu.cs.eshop.common.sdk.entity.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
public interface PageInterface<T> extends Serializable {
    void setPage(PageDTO page);

    void setRecords(List<T> records);
}
