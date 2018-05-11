package com.springapp.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 积分service
 * 
 * Created by books on 2017/11/9.
 */
@Service(value = "pointService")
public class PointService implements ApplicationListener<RegisterEvent> {
    
    @Async
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        String username = (String) event.getSource();    
        addPoint(username);
    }

    private void addPoint(String username) {
        System.out.println(Thread.currentThread().getName() + " 用户注册，增加积分：" + username);
    }
}
