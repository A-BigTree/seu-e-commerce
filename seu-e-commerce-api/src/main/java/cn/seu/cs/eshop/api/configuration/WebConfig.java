package cn.seu.cs.eshop.api.configuration;

import cn.seu.cs.eshop.api.component.AuthorUserHandlerMethodArgumentResolver;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    AuthorUserHandlerMethodArgumentResolver resolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(resolver);
    }
}
