package cs.seu.cs.eshop.common.sdk.entity.dto;

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
public class EmailSendDTO implements Serializable {
    private String from;
    private String to;
    private String subject;
    private String context;
}
