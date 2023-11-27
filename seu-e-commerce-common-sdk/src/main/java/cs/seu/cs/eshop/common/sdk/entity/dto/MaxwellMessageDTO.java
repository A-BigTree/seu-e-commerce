package cs.seu.cs.eshop.common.sdk.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/24
 */
@Data
public class MaxwellMessageDTO implements Serializable {
    private String database;
    private String table;
    private String type;
    private Long ts;
    private Long xid;
    private Boolean commit;
    private Long serverId;
    private Long threadId;
    private Map<String, String> data;
    private Map<String, String> old;
}
