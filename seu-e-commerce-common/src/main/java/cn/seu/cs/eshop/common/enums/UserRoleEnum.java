package cn.seu.cs.eshop.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 *
 * 用户角色枚举
 */
@Getter
public enum UserRoleEnum {
    // 默认情况 用于查询
    DEFAULT(-1),
    // 终极管理员
    ADMIN(0),
    // 消费者
    CUSTOMER(1),
    // 商家
    BUSINESS(2),
    // 平台运营
    PLATFORM(3),

    ;

    private final int value;

    private final static Map<Integer, UserRoleEnum> map = new HashMap<>();

    static {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            map.put(role.getValue(), role);
        }
    }

    UserRoleEnum(Integer value) {
        this.value = value;
    }

    public static UserRoleEnum valueOf(Integer value) {
        return map.getOrDefault(value, null);
    }
}
