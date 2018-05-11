package com.zookeeper.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class DistributionLockTest {

    public static void main(String[] args) {

        int count = 5;
        String host = "172.18.8.22";
        
        ExecutorService executorService = Executors.newFixedThreadPool(count);

        for (int i = 0; i < count; i++) {
            executorService.submit(new Job(new DistributionLock(host)));
        }

        executorService.shutdown();
        
    }
    
    static class Job implements Runnable {
        
        private DistributionLock lock;

        public Job(DistributionLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                log.info("获取到锁，模拟业务，休眠1秒");
                SleepUtils.second(1);
            } finally {
                lock.unlock();
            }
        }
    }

}
