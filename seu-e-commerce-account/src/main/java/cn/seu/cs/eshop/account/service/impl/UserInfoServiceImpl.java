package cn.seu.cs.eshop.account.service.impl;

import cn.seu.cs.eshop.account.cache.UserInfoCache;
import cn.seu.cs.eshop.account.convert.AccountConvert;
import cn.seu.cs.eshop.account.dao.AccountReviewDao;
import cn.seu.cs.eshop.account.dao.EshopInfoDao;
import cn.seu.cs.eshop.account.dao.UserInfoDao;
import cn.seu.cs.eshop.account.manager.EmailSendManager;
import cn.seu.cs.eshop.account.nacos.AccountNacosConfEnum;
import cn.seu.cs.eshop.account.pojo.bo.PwdGenerationConfigBO;
import cn.seu.cs.eshop.account.pojo.bo.ReviewEmailContextBO;
import cn.seu.cs.eshop.account.pojo.db.AccountReviewDO;
import cn.seu.cs.eshop.account.pojo.db.EshopInfoDO;
import cn.seu.cs.eshop.account.pojo.db.UserInfoDO;
import cn.seu.cs.eshop.account.sdk.entity.dto.AccountInfoDTO;
import cn.seu.cs.eshop.account.sdk.entity.dto.AccountReviewDTO;
import cn.seu.cs.eshop.account.sdk.entity.dto.RegisterUserListDTO;
import cn.seu.cs.eshop.account.sdk.entity.req.GetAccountInfoResponse;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoResponse;
import cn.seu.cs.eshop.account.sdk.entity.req.UpdateRegisterStateRequest;
import cn.seu.cs.eshop.account.service.UserInfoService;
import cs.seu.cs.eshop.common.sdk.enums.RegisterStateEnum;
import cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum;
import cn.seu.cs.eshop.common.nacos.ShopConf;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import cs.seu.cs.eshop.common.sdk.util.RandomGenerateUtils;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.EmailSendDTO;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.seu.cs.eshop.account.constants.AccountConstants.*;
import static cn.seu.cs.eshop.account.nacos.AccountNacosConfEnum.emailReviewContext;
import static cn.seu.cs.eshop.account.nacos.AccountNacosConfEnum.pwdGenerateConfig;
import static cn.seu.cs.eshop.common.util.MysqlUtils.buildPageData;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildFailResponse;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;

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
    @Resource
    EmailSendManager emailSendManager;
    @Resource
    ShopConf eshopConfService;
    @Resource
    UserInfoCache userInfoCache;

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
    @Transactional
    public GetAccountInfoResponse getAccountInfo(Long id) {
        UserInfoDO userInfoDO = userInfoDao.selectById(id);
        if (userInfoDO == null) {
            return buildFailResponse(GetAccountInfoResponse.class, ACCOUNT_NOT_EXIST_ERROR, null);
        }
        EshopInfoDO eshopInfoDO = eshopInfoDao.selectById(id);
        AccountReviewDO accountReviewDO = accountReviewDao.selectByAccountId(id);
        AccountReviewDTO review = null;
        if (accountReviewDO!=null) {
            review = AccountReviewDTO.builder()
                    .id(accountReviewDO.getId())
                    .accountId(accountReviewDO.getAccountId())
                    .remark(accountReviewDO.getRemark())
                    .modifier(accountReviewDO.getModifier())
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

    @Override
    @Transactional
    public BaseResponse updateRegisterState(UpdateRegisterStateRequest request) {
        UserInfoDO user = userInfoDao.selectById(request.getAccountId());
        if (user == null) {
            return buildFailResponse(BaseResponse.class, ACCOUNT_NOT_EXIST_ERROR, null);
        }
        int oldState = user.getState();
        if (!checkStateUpdate(request.getReviewState(), oldState)) {
            return buildFailResponse(BaseResponse.class, REGISTER_STATE_CHANGE_ERROR, null);
        }
        UserInfoDO userNew = new UserInfoDO();
        userNew.setId(request.getAccountId());
        userNew.setState(request.getReviewState());
        userInfoDao.updateById(userNew);
        AccountReviewDO entity = MysqlUtils.buildEffectEntity(new AccountReviewDO());
        entity.setAccountId(request.getAccountId());
        entity.setRemark(request.getRemark());
        entity.setModifier(request.getModifier());
        accountReviewDao.insert(entity);
        sendReviewEmail(user, request);
        return buildSuccessResponse(BaseResponse.class, entity.getId().toString());
    }

    private boolean checkStateUpdate(int newState, int oldState) {
        if (oldState == 0 && (newState == 1 || newState == 2)) {
            return true;
        }
        return oldState == 1 && newState == 3;
    }

    private void sendReviewEmail(UserInfoDO user, UpdateRegisterStateRequest request) {
        String from = eshopConfService.getConfig(AccountNacosConfEnum.fromEmail);
        ReviewEmailContextBO contextConf = eshopConfService.getConfigObject(emailReviewContext,
                ReviewEmailContextBO.class);
        EmailSendDTO emailSend = new EmailSendDTO();
        emailSend.setFrom(from);
        emailSend.setTo(user.getAccount());
        emailSend.setSubject(contextConf.getSubject());
        String text = contextConf.getPrefix().formatted(user.getNickname(), user.getAccount());
        int state = request.getReviewState();
        String format =contextConf.getText().get(state);
        if (state == RegisterStateEnum.REGISTER_SUCCESS.getState()) {
            PwdGenerationConfigBO pwdConf = eshopConfService.getConfigObject(pwdGenerateConfig,
                    PwdGenerationConfigBO.class);
            String pwd = RandomGenerateUtils.generateCode(pwdConf.getSymbols(), pwdConf.getLength());
            String password = DigestUtils.md5Hex(pwd + PASSWORD_ADMIN_SLAT_MD5);
            password = DigestUtils.md5Hex(password + PASSWORD_SLAT_MD5);
            UserInfoDO userNew = new UserInfoDO();
            userNew.setId(user.getId());
            userNew.setPassword(password);
            userInfoDao.updateById(userNew);
            text += format.formatted(pwd);
        } else {
            text += format.formatted(request.getRemark());
            if (state == RegisterStateEnum.BE_DELETED.getState()) {
                userInfoCache.remove(request.getAccountId());
            }
        }
        text += contextConf.getSuffix().formatted(request.getModifier());
        emailSend.setContext(text);
        emailSendManager.sendEmail(emailSend);
    }
}
