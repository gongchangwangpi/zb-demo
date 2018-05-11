package com.springapp.event.annotation;

import com.springapp.event.RegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by books on 2017/9/29.
 */
@Service(value = "email1Service")
public class Email1Service {
    
    // 注解标记监听 RegisterEvent 的事件
    @EventListener
    public void listenRegisterEvent(RegisterEvent event) {
        String username = (String) event.getSource();
//        sendEmail(agentName);
    }
    
    public void sendEmail(String username) {
        System.out.println("用户注册，发送邮件: " + username);
    }
    
}
