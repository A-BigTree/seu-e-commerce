package cn.seu.cs.eshop.account.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountReviewDTO implements Serializable {
    private Long id;
    private Long accountId;
    private String remark;
    private String createTime;
}
