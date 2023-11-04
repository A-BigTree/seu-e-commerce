package cn.seu.cs.eshop.account.service.impl;

import cn.seu.cs.eshop.account.convert.AccountConvert;
import cn.seu.cs.eshop.account.dao.AccountReviewDao;
import cn.seu.cs.eshop.account.dao.EshopInfoDao;
import cn.seu.cs.eshop.account.dao.UserInfoDao;
import cn.seu.cs.eshop.account.pojo.db.AccountReviewDO;
import cn.seu.cs.eshop.account.pojo.db.EshopInfoDO;
import cn.seu.cs.eshop.account.pojo.db.UserInfoDO;
import cn.seu.cs.eshop.account.sdk.entity.dto.AccountInfoDTO;
import cn.seu.cs.eshop.account.sdk.entity.dto.AccountReviewDTO;
import cn.seu.cs.eshop.account.sdk.entity.dto.RegisterUserListDTO;
import cn.seu.cs.eshop.account.sdk.entity.req.GetAccountInfoResponse;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoResponse;
import cn.seu.cs.eshop.account.service.UserInfoService;
import cn.seu.cs.eshop.common.enums.RegisterStateEnum;
import cn.seu.cs.eshop.common.enums.UserRoleEnum;
import cn.seu.cs.eshop.common.util.TimeUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static cn.seu.cs.eshop.common.util.MysqlUtils.buildPageData;
import static cn.seu.cs.eshop.common.util.ResponseBuilderUtils.buildFailResponse;
import static cn.seu.cs.eshop.common.util.ResponseBuilderUtils.buildSuccessResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    UserInfoDao userInfoDao;
    @Resource
    EshopInfoDao eshopInfoDao;
    @Resource
    AccountReviewDao accountReviewDao;

    @Override
    public ListRegisterInfoResponse listRegisterInfo(ListRegisterInfoRequest request) {
        UserInfoDO entity = new UserInfoDO();
        if (request.getId() != null && request.getId() > 0) {
            entity.setId(request.getId());
        }
        if (!StringUtils.isEmpty(request.getEmail())) {
            entity.setAccount(request.getEmail());
        }
        if (request.getRegisterState() != RegisterStateEnum.DEFAULT.getState()) {
            entity.setState(request.getRegisterState());
        }
        if (request.getRoleType() != UserRoleEnum.DEFAULT.getValue()) {
            entity.setRoleType(request.getRoleType());
        }
        IPage<UserInfoDO> result = userInfoDao.selectPagesByConditions(entity, request.getNickname(), request.getPage());
        RegisterUserListDTO data = buildPageData(RegisterUserListDTO.class, result, AccountConvert::convert);
        return buildSuccessResponse(ListRegisterInfoResponse.class, data);
    }

    @Override
    public GetAccountInfoResponse getAccountInfo(Long id) {
        UserInfoDO userInfoDO = userInfoDao.selectById(id);
        if (userInfoDO == null) {
            return buildFailResponse(GetAccountInfoResponse.class, "用户不存在", null);
        }
        EshopInfoDO eshopInfoDO = eshopInfoDao.selectById(id);
        AccountReviewDO accountReviewDO = accountReviewDao.selectById(id);
        AccountReviewDTO review = null;
        if (accountReviewDO!=null) {
            review = AccountReviewDTO.builder()
                    .id(accountReviewDO.getId())
                    .accountId(accountReviewDO.getAccountId())
                    .remark(accountReviewDO.getRemark())
                    .createTime(TimeUtils.convertString(accountReviewDO.getCreateTime(),
                            TimeUtils.DATE_TIME_FORMAT))
                    .build();
        }
        AccountInfoDTO data = AccountInfoDTO.builder()
                .id(userInfoDO.getId())
                .nickname(userInfoDO.getNickname())
                .account(userInfoDO.getAccount())
                .phoneNumber(userInfoDO.getPhoneNumber())
                .image(userInfoDO.getImage())
                .roleType(userInfoDO.getRoleType())
                .state(userInfoDO.getState())
                .createTime(TimeUtils.convertString(userInfoDO.getCreateTime(), TimeUtils.DATE_TIME_FORMAT))
                .review(review)
                .desc(eshopInfoDO != null ? eshopInfoDO.getShopDesc() : null)
                .build();
        return buildSuccessResponse(GetAccountInfoResponse.class, data);
    }
}
