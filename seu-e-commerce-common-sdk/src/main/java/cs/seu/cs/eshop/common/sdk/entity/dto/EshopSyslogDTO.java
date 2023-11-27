package cs.seu.cs.eshop.common.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopSyslogDTO {
    private Long userId;
    private String ipAddress;
    private String apiType;
    private String apiName;
    private String parameters;
    private String result;
    private String exception;
    private String time;
}
