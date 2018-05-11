package com.guava.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * @author zhangbo
 */
public class EventBusTest {

    public static void main(String[] args) {

        EventBus eventBus = new EventBus();

        /**
         * 注册事件监听器
         * 监听器中要有包含{@link com.google.common.eventbus.Subscribe}注解的方法
         */
        eventBus.register(new EventListener());
        
        // 发送事件
        eventBus.post(new Event("test msg", 200));
    }
    
}
