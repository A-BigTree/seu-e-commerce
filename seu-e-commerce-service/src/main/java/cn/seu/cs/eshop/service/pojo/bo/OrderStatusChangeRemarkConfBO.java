package cn.seu.cs.eshop.service.pojo.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Data
public class OrderStatusChangeRemarkConfBO implements Serializable {
    Map<Integer, String> remarks;

    public String getRemarkByStatus(Integer status) {
        return remarks.get(status);
    }
}
