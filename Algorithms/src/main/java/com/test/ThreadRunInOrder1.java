package com.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 3个线程，按顺序执行
 *
 * @author zhangbo
 */
@Slf4j
public class ThreadRunInOrder1 {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Thread prev = Thread.currentThread();
        for (int i = 0; i < 5; i++) {
            Job job = new Job("Thread - " + i, prev);
            prev = job;
            job.start();
            TimeUnit.SECONDS.sleep(1);
        }
        LockSupport.unpark(prev);
        executorService.shutdown();
    }

    @Slf4j
    static class Job extends Thread {
        private Thread prev;

        public Job(String name, Thread prev) {
            super(name);
            this.prev = prev;
        }

        @Override
        public void run() {
            try {
                // 唤醒上一个线程
                LockSupport.unpark(prev);
                // 暂停当前线程
                LockSupport.park();
                log.info("... run");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
