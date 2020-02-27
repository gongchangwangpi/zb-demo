package com.zb.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

/**
 * @author zhangbo
 * @date 2019-11-19
 */
@Configuration
public class RedisHeaderSessionConfig {

    @Bean
    public HeaderHttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

}
