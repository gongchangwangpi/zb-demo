package com.springapp.event.annotation;

import com.springapp.event.RegisterEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by books on 2017/9/29.
 */
@Service(value = "register1Service")
public class Register1Service {
    
    // ApplicationEventPublisher 由spring自动注入
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    
    public void register(String username) {
        System.out.println("用户注册成功: " + username);
        applicationEventPublisher.publishEvent(new RegisterEvent(username));
    }
    
}
