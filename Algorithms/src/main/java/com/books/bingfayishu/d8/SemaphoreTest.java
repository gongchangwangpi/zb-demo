package com.books.bingfayishu.d8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

import com.books.bingfayishu.d4.SleepUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class SemaphoreTest {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5);

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(30);
        executorService.prestartAllCoreThreads();

        log.info("----- main start");

        for (int i = 0; i < 30; i++) {
//            SleepUtils.millis(500);
            executorService.execute(() -> {
                try {
                    log.info(" >>>>> acquire");
                    semaphore.acquire();
                    // 最多同时有5个线程进入
                    log.info(" ==== run");
                    SleepUtils.second(2);

                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        
        executorService.shutdown();

    }
    
}
