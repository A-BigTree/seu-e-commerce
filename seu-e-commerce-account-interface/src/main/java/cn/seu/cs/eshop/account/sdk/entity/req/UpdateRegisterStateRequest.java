package cn.seu.cs.eshop.account.sdk.entity.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@Data
public class UpdateRegisterStateRequest implements Serializable {
    private Long accountId;
    private String remark;
    private Integer reviewState;
    private String modifier;
}
