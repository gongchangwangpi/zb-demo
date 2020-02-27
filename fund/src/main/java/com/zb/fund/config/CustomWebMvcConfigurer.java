package com.zb.fund.config;

import com.zb.fund.web.serialize.DateHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author zhangbo
 * @date 2019-08-15
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new DateHandlerMethodArgumentResolver());
    }
}
