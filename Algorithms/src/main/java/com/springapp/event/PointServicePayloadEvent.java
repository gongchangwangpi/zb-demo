package com.springapp.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 积分service
 * 
 * Created by books on 2017/11/9.
 */
@Slf4j
@Service(value = "pointServicePayloadEvent")
public class PointServicePayloadEvent implements ApplicationListener<PayloadApplicationEvent<RegisterEventObject>> {
    
    @Async
    @Override
    public void onApplicationEvent(PayloadApplicationEvent<RegisterEventObject> event) {
        addPoint(event.getPayload().getUsername());
    }

    private void addPoint(String username) {
        log.info(Thread.currentThread().getName() + " 用户注册，增加积分：" + username);
    }
}
