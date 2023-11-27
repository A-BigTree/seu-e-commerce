package cs.seu.cs.eshop.common.sdk.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/24
 */
@Getter
public enum MaxwellTypeEnum {
    DEFAULT("default"),
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    CREATE_TABLE("table-create"),
    ALTER_TABLE("table-alter"),
    ;

    final String type;

    public static final Map<String, MaxwellTypeEnum> TYPE_MAP = new HashMap<>();

    static {
        for (MaxwellTypeEnum type : MaxwellTypeEnum.values()) {
            TYPE_MAP.put(type.getType(), type);
        }
    }

    MaxwellTypeEnum(String type) {
        this.type = type;
    }

    public static MaxwellTypeEnum getBinlogType(String type) {
        return TYPE_MAP.getOrDefault(type, DEFAULT);
    }
}
