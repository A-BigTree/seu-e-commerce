package cn.seu.cs.eshop.api.aop;

import cn.seu.cs.eshop.account.sdk.entity.dto.UserBaseDTO;
import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.api.cache.UserTokenCache;
import cn.seu.cs.eshop.common.enums.ResponseStateEnum;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
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

import static cn.seu.cs.eshop.api.constants.ApiConstants.AUTHORIZATION_HEADER;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/26
 */
@Slf4j
@Aspect
@Component
public class ApiMonitorAspect {
    @Resource
    UserTokenCache userTokenCache;

    @SuppressWarnings("unchecked")
    @Around("@annotation(cn.seu.cs.eshop.api.annotation.ApiMonitor)")
    public Object authorizationFilter(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<? extends BaseResponseInterface<Object>> clazz =
                (Class<? extends BaseResponseInterface<Object>>) method.getGenericReturnType();

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return ResponseBuilderUtils.buildErrorResponse(clazz, null);
        }
        ApiMonitor apiMonitor = method.getAnnotation(ApiMonitor.class);
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        // 鉴权
        if (apiMonitor.isAuthor()) {
            String token = request.getHeader(AUTHORIZATION_HEADER);
            // 未登录
            if (StringUtils.isEmpty(token)) {
                return ResponseBuilderUtils.buildResponse(clazz,
                        ResponseStateEnum.AUTHORIZATION, null);
            }
            // 登录过期
            UserBaseDTO user = userTokenCache.getUserTokenInfo(token);
            if (user == null) {
                return ResponseBuilderUtils.buildResponse(clazz,
                        ResponseStateEnum.AUTHORIZATION_EXPIRATION, null);
            }
            // 权限错误
            if (!Objects.equals(user.getRoleType(), apiMonitor.roleType().getValue())) {
                return ResponseBuilderUtils.buildResponse(clazz,
                        ResponseStateEnum.PRIVILEGES_ERROR, null);
            }
            userTokenCache.setUserTokenInfo(token, user);
        }
        // 请求统计
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Method: {} exec error, e: ", method, e);
            return ResponseBuilderUtils.buildErrorResponse(clazz, null);
        }
    }
}
