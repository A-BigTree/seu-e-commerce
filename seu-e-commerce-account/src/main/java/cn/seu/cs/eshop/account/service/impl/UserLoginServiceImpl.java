package cn.seu.cs.eshop.account.service.impl;

import cn.seu.cs.eshop.account.dao.EmailVerifyDao;
import cn.seu.cs.eshop.account.dao.UserInfoDao;
import cn.seu.cs.eshop.account.nacos.AccountNacosConfEnum;
import cn.seu.cs.eshop.account.pojo.db.UserInfoDO;
import cn.seu.cs.eshop.account.sdk.entity.dto.EshopSessionDTO;
import cn.seu.cs.eshop.account.sdk.entity.dto.UserInfoDTO;
import cn.seu.cs.eshop.account.sdk.entity.req.*;
import cn.seu.cs.eshop.common.enums.ResponseStateEnum;
import cn.seu.cs.eshop.common.enums.UserRoleEnum;
import cn.seu.cs.eshop.common.nacos.ShopConf;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.common.util.RandomGenerateUtils;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import cn.seu.cs.eshop.account.cache.EmailVerifyCache;
import cn.seu.cs.eshop.account.cache.UserInfoCache;
import cn.seu.cs.eshop.account.constants.AccountConstants;
import cn.seu.cs.eshop.account.pojo.db.EmailVerifyDO;
import cn.seu.cs.eshop.account.service.UserLoginService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {
    @Resource
    ShopConf eshopConfService;
    @Resource
    EmailVerifyDao emailVerifyDao;
    @Resource
    EmailVerifyCache emailVerifyCache;
    @Resource
    JavaMailSender javaMailSender;
    @Resource
    UserInfoDao userInfoDao;
    @Resource
    UserInfoCache userInfoCache;


    @Override
    @Transactional
    public BaseResponse sendVerifyEmail(SendVerifyEmailRequest request) {
        EmailVerifyDO emailVerifyDO = emailVerifyCache.getEmailVerifyDO(request.getToEmail());
        if (emailVerifyDO != null) {
            return ResponseBuilderUtils.buildResponse(BaseResponse.class,
                    ResponseStateEnum.OPERATION_ERROR, AccountConstants.VERIFY_CODE_RETRY_ERROR);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        String from = request.getFromEmail();
        if (StringUtils.isEmpty(from)) {
            from = eshopConfService.getConfig(AccountNacosConfEnum.fromEmail);
        }
        message.setFrom(from);
        message.setTo(request.getToEmail());
        int length = eshopConfService.getConfigObject(AccountNacosConfEnum.emailVerifyLength, Integer.class);
        String symbols = eshopConfService.getConfig(AccountNacosConfEnum.emailVerifySymbols);
        String verifyCode = RandomGenerateUtils.generateVerCode(symbols, length);
        String context = request.getContext();
        if (StringUtils.isEmpty(context)) {
            String format = eshopConfService.getConfig(AccountNacosConfEnum.emailContext);
            context = String.format(format, request.getToEmail(), verifyCode,
                    eshopConfService.getConfigObject(AccountNacosConfEnum.emailVerifyTime, Long.class).toString());
        }
        message.setText(context);
        message.setSubject("注册验证码");
        EmailVerifyDO entity = MysqlUtils.buildEffectEntity(new EmailVerifyDO());
        entity.setAccount(request.getToEmail());
        entity.setVerifyCode(verifyCode);
        emailVerifyDao.insert(entity);
        emailVerifyCache.setEmailVerifyDO(entity);
        javaMailSender.send(message);
        return ResponseBuilderUtils.buildSuccessResponse(BaseResponse.class, AccountConstants.VERIFY_CODE_SEND_SUCCESS);
    }

    @Override
    @Transactional
    public BaseResponse registerUser(RegisterUserRequest request) {
        String account = request.getAccount();
        String verifyCode = request.getVerifyCode();
        EmailVerifyDO emailVerifyDO = emailVerifyCache.getEmailVerifyDO(account);
        if (emailVerifyDO == null || !verifyCode.equals(emailVerifyDO.getVerifyCode())) {
            return ResponseBuilderUtils.buildResponse(BaseResponse.class,
                    ResponseStateEnum.OPERATION_ERROR, AccountConstants.VERIFY_CODE_CHECK_ERROR);
        }
        String password = DigestUtils.md5Hex(request.getPassword() +
                AccountConstants.PASSWORD_SLAT_MD5);
        UserInfoDO entity = MysqlUtils.buildEffectEntity(new UserInfoDO());
        entity.setAccount(account);
        entity.setPassword(password);
        entity.setNickname(RandomGenerateUtils.generateNickname(AccountConstants.NICKNAME_PREFIX,
                AccountConstants.NICKNAME_SYMBOLS, AccountConstants.NICKNAME_CODE_LENGTH));
        if(!StringUtils.isEmpty(request.getNickname())) {
            entity.setNickname(request.getNickname());
        }
        entity.setRoleType(UserRoleEnum.CUSTOMER.getValue());
        if (request.getRoleType() >= 0) {
            entity.setRoleType(request.getRoleType());
        }
        UserInfoDO userInfoDO = userInfoDao.selectByAccountAndRole(account, null, entity.getRoleType());
        if (userInfoDO != null) {
            return ResponseBuilderUtils.buildResponse(BaseResponse.class,
                    ResponseStateEnum.OPERATION_ERROR, AccountConstants.ACCOUNT_DUPLICATION_ERROR);
        }
        int id = userInfoDao.insert(entity);
        return ResponseBuilderUtils.buildSuccessResponse(BaseResponse.class, Integer.toString(id));
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest request) {
        String account = request.getAccount();
        String password = DigestUtils.md5Hex(request.getPassword() +
                AccountConstants.PASSWORD_SLAT_MD5);
        log.info(password);
        UserInfoDO userInfoDO = userInfoDao.selectByAccountAndRole(account, password, request.getRoleType());
        if (userInfoDO == null) {
            return ResponseBuilderUtils.buildResponse(LoginUserResponse.class,
                    ResponseStateEnum.OPERATION_ERROR, new EshopSessionDTO());
        }
        EshopSessionDTO session = buildNewSession(userInfoCache.getUserInfo(userInfoDO.getId()));
        return ResponseBuilderUtils.buildSuccessResponse(LoginUserResponse.class, session);
    }

    @Override
    public GetUserInfoResponse getUserInfo(Long id) {
        EshopSessionDTO session = buildNewSession(userInfoCache.getUserInfo(id));
        return ResponseBuilderUtils.buildSuccessResponse(GetUserInfoResponse.class, session);
    }

    public EshopSessionDTO buildNewSession(UserInfoDO userInfoDO) {
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .id(userInfoDO.getId())
                .nickname(userInfoDO.getNickname())
                .image(StringUtils.isEmpty(userInfoDO.getImage()) ?
                        StringUtils.EMPTY : userInfoDO.getImage())
                .account(userInfoDO.getAccount())
                .roleType(userInfoDO.getRoleType())
                .phoneNumber(userInfoDO.getPhoneNumber())
                .build();
        String token = UUID.randomUUID().toString() + userInfoDO.getId();
        return EshopSessionDTO.builder()
                .id(userInfoDO.getId())
                .token(token)
                .user(userInfoDTO)
                .slots(new HashMap<>())
                .build();
    }


}
