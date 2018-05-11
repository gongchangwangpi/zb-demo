package com.guava.eventbus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;

/**
 * 异步事件总线
 * 
 * @author zhangbo
 */
public class AsyncEventBusTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        AsyncEventBus eventBus = new AsyncEventBus(executorService);

        /**
         * 注册事件监听器
         * 监听器中要有包含{@link com.google.common.eventbus.Subscribe}注解的方法
         */
        eventBus.register(new EventListener());

        // 发送事件
        eventBus.post(new Event("test msg", 200));

        executorService.shutdown();
    }
    
}
