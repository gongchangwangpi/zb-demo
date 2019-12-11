package com.guava.util;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 限流
 * 
 * Created by books on 2017/12/21.
 */
@Slf4j
public class RateLimiterTest {

    private static final RateLimiter rateLimiter = RateLimiter.create(0.1);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                rateLimiter.acquire();
                log.info(" run --->>> {}", System.currentTimeMillis());
            });
        }

        executorService.shutdown();
        
    }
    
}
