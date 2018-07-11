package com.springapp.event.annotation;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Created by books on 2017/9/29.
 */
@Service(value = "register2Service")
public class Register2Service {
    
    // ApplicationEventPublisher 由spring自动注入
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    
    public void register(String username) {
        System.out.println("用户注册成功: " + username);
        applicationEventPublisher.publishEvent(username);
    }
    
}
