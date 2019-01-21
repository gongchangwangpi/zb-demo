package com.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3个线程，按顺序执行
 *
 * @author zhangbo
 */
@Slf4j
public class ThreadRunInOrder2 {
    
    private static final ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                executorService.execute(new RunJob(i));
                TimeUnit.SECONDS.sleep(1);
            } finally {
                lock.unlock();
            }

        }
        
        executorService.shutdown();
    }

    @Slf4j
    static class RunJob implements Runnable {
        private int flag;

        public RunJob(int flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                log.info("... run == {}", flag);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}
