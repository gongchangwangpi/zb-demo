package com.guava.util;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * 限流
 * 
 * Created by books on 2017/12/21.
 */
public class RateLimiterTest {

    public static void main(String[] args) throws InterruptedException {

        RateLimiter rateLimiter = RateLimiter.create(5.0);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            
            rateLimiter.acquire(1);
            System.out.println(i + "\t --- " + System.currentTimeMillis());
            if (i == 5) {
                TimeUnit.SECONDS.sleep(5);
            }
            
        }

        System.out.println(System.currentTimeMillis() - start);
        
    }
    
}
