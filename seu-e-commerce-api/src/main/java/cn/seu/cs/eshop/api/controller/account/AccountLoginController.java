package cn.seu.cs.eshop.api.controller.account;

import cn.seu.cs.eshop.account.sdk.entity.dto.EshopSessionDTO;
import cn.seu.cs.eshop.account.sdk.entity.req.*;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.api.cache.UserTokenCache;
import cn.seu.cs.eshop.api.constants.ApiConstants;
import cn.seu.cs.eshop.api.dto.UserBaseDTO;
import cn.seu.cs.eshop.common.component.SftpUtil;
import cn.seu.cs.eshop.common.enums.UserRoleEnum;
import cn.seu.cs.eshop.common.util.JsonUtils;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import cn.seu.cs.eshop.common.util.TimeUtils;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

import static cn.seu.cs.eshop.api.constants.ApiConstants.*;
import static cn.seu.cs.eshop.common.enums.ResponseStateEnum.OK;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/20
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountLoginController {
    @DubboReference(timeout = 4000, retries = 0)
    EshopAccountService eshopAccountService;
    @Resource
    UserTokenCache userTokenCache;
    @Resource
    SftpUtil sftp;

    @ApiMonitor(isAuthor = false)
    @CrossOrigin
    @PostMapping("/send/email/verify")
    public BaseResponse sendEmail(@RequestBody SendVerifyEmailRequest request) {
        return eshopAccountService.sendVerifyEmail(request);
    }

    @ApiMonitor(isAuthor = false)
    @CrossOrigin
    @PostMapping("/user/register")
    public BaseResponse registerUser(@RequestBody RegisterUserRequest request) {
        return eshopAccountService.registerUser(request);
    }

    @ApiMonitor(isAuthor = false)
    @CrossOrigin
    @PostMapping("/user/login")
    public LoginUserResponse loginUser(@RequestBody LoginUserRequest request) {
        LoginUserResponse response = eshopAccountService.loginUser(request);
        EshopSessionDTO session = response.getData();
        if (Objects.equals(response.getCode(), OK.getCode()) && session != null) {
            userTokenCache.setUserTokenInfo(session.getToken(),
                    UserBaseDTO.builder()
                    .id(session.getId())
                    .account(session.getUser().getAccount())
                    .roleType(session.getUser().getRoleType())
                    .build());
        }
        return response;
    }

    @ApiMonitor
    @CrossOrigin
    @PostMapping("/user/logout")
    public BaseResponse logoutUser(@RequestHeader(ApiConstants.AUTHORIZATION_HEADER) String token) {
        userTokenCache.removeToken(token);
        return ResponseBuilderUtils.buildSuccessResponse(BaseResponse.class, "OK");
    }

    @ApiMonitor
    @CrossOrigin
    @GetMapping("/user/info/get")
    public GetUserInfoResponse getUserInfo(@AuthorUserInfo Long id) {
        return eshopAccountService.getUserInfo(id);
    }

    @ApiMonitor
    @CrossOrigin
    @PostMapping("/user/head/load")
    public BaseResponse loadUserHead(MultipartFile photo, @AuthorUserInfo Long id) {
        try {
            InputStream image = photo.getInputStream();
            String fileName = photo.getOriginalFilename();
            String suffix = DEFAULT_SUFFIX;
            if (fileName != null && !StringUtils.isEmpty(fileName.substring(fileName.lastIndexOf(".")))) {
                suffix = fileName.substring(fileName.lastIndexOf("."));
            }
            String prefix = buildString(IMAGE_HEADER_PREFIX, Long.toString(id % 10));
            String file = "" + id + TimeUtils.getCurrentTime() + suffix;
            sftp.upload(prefix, file, image);
            UpdateUserInfoRequest request = new UpdateUserInfoRequest();
            request.setImage("/head" + id % 10 + "/" + file);
            return eshopAccountService.updateUserInfo(id, request);
        } catch (Exception e) {
            log.error("Id: {}, Image update error. e: ", id, e);
            return ResponseBuilderUtils.buildErrorResponse(BaseResponse.class, "Error");
        }
    }

    @ApiMonitor(isAuthor = false)
    @CrossOrigin
    @PostMapping("/user/shop/register")
    public BaseResponse registerAdminUser(@RequestParam("photo") MultipartFile photo,
                                          @RequestParam("userInfo") String shopInfo) {
        try {
            InputStream image = photo.getInputStream();
            String fileName = photo.getOriginalFilename();
            log.info(fileName);
            String suffix = DEFAULT_SUFFIX;
            if (fileName != null && !StringUtils.isEmpty(fileName.substring(fileName.lastIndexOf(".")))) {
                suffix = fileName.substring(fileName.lastIndexOf("."));
            }
            String file = TimeUtils.getCurrentTime() + suffix;
            RegisterUserRequest request = JsonUtils.jsonToObject(shopInfo, RegisterUserRequest.class);
            request.setImage(file);
            BaseResponse response = eshopAccountService.registerUser(request);
            if (!Objects.equals(response.getCode(), OK.getCode())) {
                return ResponseBuilderUtils.buildErrorResponse(BaseResponse.class, "Error");
            }
            long id = Long.parseLong(response.getData());
            String prefix = buildString(IMAGE_SHOP_HEADER_PREFIX, String.valueOf(id % 10));
            file = id + file;
            sftp.upload(prefix, file, image);
            return response;
        } catch (Exception e) {
            log.error("Image update error. e: ", e);
            return ResponseBuilderUtils.buildErrorResponse(BaseResponse.class, "Error");
        }
    }

    @ApiMonitor(roleType = UserRoleEnum.PLATFORM)
    @CrossOrigin
    @PostMapping("/user/register/list")
    public ListRegisterInfoResponse listRegisterUserInfo(@RequestBody ListRegisterInfoRequest request) {
        return eshopAccountService.listRegisterUserInfo(request);
    }

    // TODO 账号审核

    // TODO 封号操作
}
