package com.springapp.beans;

import com.springapp.beans.service.IUserListener;
import com.springapp.beans.service.UserAgeListener;
import com.springapp.beans.service.UsernameListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbo
 * @date 2019-11-08
 */
@Configuration
public class BeanConfig {

    @Bean
    public IUserListener usernameListener() {
        return new UsernameListener();
    }

    @Bean
    public IUserListener userAgeListener() {
        return new UserAgeListener();
    }

}
