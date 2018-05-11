package com.books.bingfayishu.d8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * @author zhangbo
 */
public class SemaphoreTest {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5);

        ExecutorService executorService = Executors.newFixedThreadPool(30);

        for (int i = 0; i < 30; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    // 最多同时有5个线程进入
                    System.out.println("...");
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
