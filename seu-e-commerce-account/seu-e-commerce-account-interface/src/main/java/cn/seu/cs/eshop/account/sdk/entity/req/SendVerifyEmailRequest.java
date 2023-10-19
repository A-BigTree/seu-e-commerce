package cn.seu.cs.eshop.account.sdk.entity.req;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Data
@Builder
public class SendVerifyEmailRequest {
    private String fromEmail;
    private String toEmail;
    private String context;
}
