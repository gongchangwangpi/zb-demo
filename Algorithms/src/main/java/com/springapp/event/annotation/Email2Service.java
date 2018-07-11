package com.springapp.event.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by books on 2017/9/29.
 */
@Slf4j
@Service(value = "email2Service")
public class Email2Service {
    
    // 注解标记监听 RegisterEvent 的事件
    @EventListener(value = String.class)
    @Async
    public void listenRegisterEvent(String username) {
        sendEmail(username);
    }
    
    public void sendEmail(String username) {
        log.info("用户注册成功，发送邮件: {}", username);
    }
    
}
