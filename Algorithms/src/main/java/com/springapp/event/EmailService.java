package com.springapp.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 邮件服务,由spring管理,实现ApplicationListener,监听自己感兴趣的事件,完成相应的业务逻辑
 * 
 * Created by books on 2017/9/29.
 */
@Service(value = "emailService")
public class EmailService implements ApplicationListener<RegisterEvent> {

    // 要实现异步调用，需要配置 TaskExecutor
    // 同步的情况下，如果订阅事件方出现异常，会影响(终止)发布事件方的后续代码执行
    // 异步的情况不会影响发布事件方
    @Async
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        String username = (String) event.getSource();
        sendEmail(username);
    }

    public void sendEmail(String username) {
        System.out.println(Thread.currentThread().getName() + " 用户注册，发送邮件：" + username);
        throw new RuntimeException("发送邮件出现异常");
    }
}
