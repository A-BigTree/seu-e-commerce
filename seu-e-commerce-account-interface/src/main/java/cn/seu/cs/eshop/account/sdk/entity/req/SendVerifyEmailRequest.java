package cn.seu.cs.eshop.account.sdk.entity.req;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Data
public class SendVerifyEmailRequest implements Serializable {
    private String fromEmail;
    private String toEmail;
    private String context;
}
