package cs.seu.cs.eshop.common.sdk.enums;

import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/24
 */
@Getter
public enum MaxwellTypeEnum {
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    CREATE_TABLE("table-create"),
    ALTER_TABLE("table-alter"),
    ;

    final String type;

    MaxwellTypeEnum(String type) {
        this.type = type;
    }

    public boolean equalType(String type) {
        return this.type.equals(type);
    }
}
