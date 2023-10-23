package cn.seu.cs.eshop.api.aop;

import cn.seu.cs.eshop.account.sdk.entity.dto.UserBaseDTO;
import cn.seu.cs.eshop.api.cache.UserTokenCache;
import cn.seu.cs.eshop.common.enums.ResponseStateEnum;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

import static cn.seu.cs.eshop.api.constants.ApiConstants.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/23
 */
@Component
@Aspect
@Slf4j
public class AuthorizationMonitorAspect {
    @Resource
    UserTokenCache userTokenCache;

    @Around("@annotation(cn.seu.cs.eshop.api.aop.AuthorizationMonitor)")
    public Object authorizationFilter(ProceedingJoinPoint joinPoint) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return ResponseBuilderUtils.buildFailResponse(BaseResponse.class,
                    AUTHORIZATION_ERROR);
        }
        HttpServletRequest request = ((ServletRequestAttributes)attributes).getRequest();
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(token)) {
            return ResponseBuilderUtils.buildResponse(BaseResponse.class,
                    ResponseStateEnum.AUTHORIZATION, NO_AUTHORIZATION_ERROR);
        }
        UserBaseDTO user = userTokenCache.getUserTokenInfo(token);
        if (user == null) {
            return ResponseBuilderUtils.buildResponse(BaseResponse.class,
                    ResponseStateEnum.AUTHORIZATION_EXPIRATION, AUTHORIZATION_EXPIRATION_ERROR);
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AuthorizationMonitor authorization = method.getAnnotation(AuthorizationMonitor.class);
        if (!Objects.equals(user.getRoleType(), authorization.roleType().getValue())) {
            return ResponseBuilderUtils.buildResponse(BaseResponse.class,
                    ResponseStateEnum.PRIVILEGES_ERROR, PRIVILEGES_ERROR);
        }
        if (authorization.needId()) {
            // TODO 转换ID
        }
        Object res = null;
        try {
            res = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Method: {} exec error, e: ", method.toString(), e);
            return ResponseBuilderUtils.buildFailResponse(BaseResponse.class, API_SERVER_ERROR);
        }
        userTokenCache.setUserTokenInfo(token, user);
        return res;
    }
}
