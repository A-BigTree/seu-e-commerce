package cn.seu.cs.eshop.api.component;

import cn.seu.cs.eshop.account.sdk.entity.dto.UserInfoDTO;
import cn.seu.cs.eshop.api.annotation.AuthorUserInfo;
import cn.seu.cs.eshop.api.cache.UserTokenCache;
import cn.seu.cs.eshop.api.constants.ApiConstants;
import cn.seu.cs.eshop.api.dto.UserBaseDTO;
import jakarta.annotation.Resource;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/24
 */
@Component
public class AuthorUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private UserTokenCache userTokenCache;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorUserInfo.class) &&
                (parameter.getParameterType().isAssignableFrom(Long.class) ||
                        parameter.getParameterType().isAssignableFrom(UserInfoDTO.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader(ApiConstants.AUTHORIZATION_HEADER);
        UserBaseDTO user = userTokenCache.getUserTokenInfo(token);
        if (parameter.getParameterType().isAssignableFrom(Long.class)) {
            return user.getId();
        }
        if (parameter.getParameterType().isAssignableFrom(UserInfoDTO.class)) {
            return user;
        }
        throw new Exception("This annotation only can be injected in class 'Long' or 'UserBaseDTO'");
    }
}
