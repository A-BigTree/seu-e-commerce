package cn.seu.cs.eshop.api.util;

import cn.seu.cs.eshop.api.dto.UserBaseDTO;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;
import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.BUSINESS;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
public class ApiUtil {
    public static Long getShopId(UserBaseDTO user) {
        return user.getRoleType() == BUSINESS.getValue() ? user.getId():OFFICIAL_ID;
    }

    public static Long getSearchShopId(UserBaseDTO user, Long shopId) {
        return user.getRoleType() == BUSINESS.getValue() ? user.getId() : shopId;
    }
}
