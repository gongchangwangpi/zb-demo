package com.springapp.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbo
 */
@Configuration
public class BeanConfig {
    
    @Bean
    public UserService userService() {
        return new UserService();
    }
    
    @Bean
    public RetryHandler retryHandler() {
        return new RetryHandler();
    }
    
}
