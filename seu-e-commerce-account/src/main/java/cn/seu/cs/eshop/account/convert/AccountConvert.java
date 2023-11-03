package cn.seu.cs.eshop.account.convert;

import cn.seu.cs.eshop.account.pojo.db.UserInfoDO;
import cn.seu.cs.eshop.account.sdk.entity.dto.RegisterUserInfoDTO;
import cn.seu.cs.eshop.common.util.TimeUtils;

import static cn.seu.cs.eshop.common.util.TimeUtils.DATE_TIME_FORMAT;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
public class AccountConvert {
    public static RegisterUserInfoDTO convert(UserInfoDO userInfoDO) {
        return RegisterUserInfoDTO.builder()
                .id(userInfoDO.getId())
                .nickname(userInfoDO.getNickname())
                .account(userInfoDO.getAccount())
                .phoneNumber(userInfoDO.getPhoneNumber())
                .image(userInfoDO.getImage())
                .roleType(userInfoDO.getRoleType())
                .registerState(userInfoDO.getState())
                .createTime(TimeUtils.convertString(userInfoDO.getCreateTime(), DATE_TIME_FORMAT))
                .build();
    }
}
