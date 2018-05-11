package com.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class RedisDistrubutionLockTest {

    public static void main(String[] args) {
        
        int count = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(count);

        for (int i = 0; i < count; i++) {
            executorService.submit(new Job(new RedisDistributionLock()));
        }
        
        executorService.shutdown();
    }
    
    static class Job implements Runnable {
        private RedisDistributionLock lock;

        public Job(RedisDistributionLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                
                log.info("获取到锁，模拟业务耗时，休眠3秒");
                SleepUtils.second(3);
                
            } finally {
                lock.unlock();
            }
        }
    }
}
