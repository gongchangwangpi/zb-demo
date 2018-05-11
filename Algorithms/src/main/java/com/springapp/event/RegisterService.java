package com.springapp.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * 用户注册服务，需由spring管理,实现 ApplicationEventPublisherAware,在实现业务逻辑后,发布注册事件
 * 
 * Created by books on 2017/9/29.
 */
@Service(value = "registerService")
public class RegisterService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    public void register(String username) {
        System.out.println(Thread.currentThread().getName() + " 用户: " + username + " 注册成功");
        // 发布事件
        applicationEventPublisher.publishEvent(new RegisterEvent(username));

        System.out.println("注册事件发布完毕");
    }
}
