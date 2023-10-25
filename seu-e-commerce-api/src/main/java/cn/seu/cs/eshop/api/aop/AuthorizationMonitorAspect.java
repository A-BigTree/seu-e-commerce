package cn.seu.cs.eshop.api.aop;

import cn.seu.cs.eshop.account.sdk.entity.dto.UserBaseDTO;
import cn.seu.cs.eshop.api.annotation.AuthorizationMonitor;
import cn.seu.cs.eshop.api.cache.UserTokenCache;
import cn.seu.cs.eshop.common.enums.ResponseStateEnum;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
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

    @SuppressWarnings("unchecked")
    @Around("@annotation(cn.seu.cs.eshop.api.annotation.AuthorizationMonitor)")
    public Object authorizationFilter(ProceedingJoinPoint joinPoint) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return ResponseBuilderUtils.buildFailResponse(BaseResponse.class,
                    AUTHORIZATION_ERROR);
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<? extends BaseResponseInterface<Object>> clazz =
                (Class<? extends BaseResponseInterface<Object>>)method.getGenericReturnType();
        HttpServletRequest request = ((ServletRequestAttributes)attributes).getRequest();
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(token)) {
            return ResponseBuilderUtils.buildResponse(clazz,
                    ResponseStateEnum.AUTHORIZATION, null);
        }
        UserBaseDTO user = userTokenCache.getUserTokenInfo(token);
        if (user == null) {
            return ResponseBuilderUtils.buildResponse(clazz,
                    ResponseStateEnum.AUTHORIZATION_EXPIRATION, null);
        }

        AuthorizationMonitor authorization = method.getAnnotation(AuthorizationMonitor.class);
        if (!Objects.equals(user.getRoleType(), authorization.roleType().getValue())) {
            return ResponseBuilderUtils.buildResponse(clazz, ResponseStateEnum.PRIVILEGES_ERROR, null);
        }
        userTokenCache.setUserTokenInfo(token, user);
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Method: {} exec error, e: ", method, e);
            return ResponseBuilderUtils.buildFailResponse(clazz, null);
        }
    }
}
