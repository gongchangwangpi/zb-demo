package com.guava.common.cache;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.books.bingfayishu.d4.SleepUtils;
import com.google.common.cache.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 该缓存的后台清理不会定时的自动进行
 * 一般在对缓存进行写操作时顺带进行清理工作，避免额外的线程开销共，和与用户线程共同操作缓存而带来的锁的竞争
 * 
 * 或者用如下的方式，自己创建线程来定时清理缓存
 * 
 * @author zhangbo
 */
@Slf4j
public class CacheTest {

    public static void main(String[] args) {

        RemovalListener<Object, Object> removalListener = RemovalListeners.asynchronous(new RemovalListener<Object, Object>() {
            // 异步监听
            @Override
            public void onRemoval(RemovalNotification<Object, Object> notification) {
                log.warn("removing key: {}, value: {}, cause: {}", notification.getKey(), notification.getValue(), notification.getCause());
            }
        }, Executors.newFixedThreadPool(2));

        // 创建缓存器
        Cache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(10)
                .expireAfterAccess(1000, TimeUnit.MICROSECONDS)
                .removalListener(removalListener)
                .build();

        // 自己动手，创建定时清理缓存的线程池
//        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                cache.cleanUp();
//            }
//        }, 5000, 5000, TimeUnit.MICROSECONDS);
        

        cache.put("1", "1");
        SleepUtils.millis(800);
        cache.put("2", "2");
        SleepUtils.millis(800);
        cache.put("3", "3");
        SleepUtils.millis(800);
        
        SleepUtils.second(3);

    }
    
}
