package com.guava.util;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * 限流，多线程
 * 
 * Created by books on 2017/12/21.
 */
public class RateLimiterMultiThreadTest {
    
    // 初始化限流，每秒最大5个调用权限
    static RateLimiter rateLimiter = RateLimiter.create(50.0);

    public static void main(String[] args) {

        
        for (int i = 0; i < 100; i++) {
            
            new Thread(() -> {
                // 因每个请求占用5秒，所以每次获取5个权限，等于每秒执行一个请求
                // 如果设置每次获取1个权限，等于每秒接收5个请求
//                rateLimiter.acquire(1);
//                System.out.println(Thread.currentThread() + "  \t--  " + System.currentTimeMillis());
                
                if(rateLimiter.tryAcquire(1, 500L, TimeUnit.MILLISECONDS)) {
                    System.out.println(Thread.currentThread() + "  \t--  " + System.currentTimeMillis());
                }
                /*try {
                    // 模拟任务执行时间5秒
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }).start();
            
        }
    }
    
}
