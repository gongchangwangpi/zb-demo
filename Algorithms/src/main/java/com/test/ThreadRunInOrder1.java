package com.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 3个线程，按顺序执行
 *
 * @author zhangbo
 */
@Slf4j
public class ThreadRunInOrder1 {

    public static void main(String[] args) throws InterruptedException {

        Thread prev = Thread.currentThread();
        
        for (int i = 0; i < 5; i++) {
            Job job = new Job("Thread - " + i, prev);
            prev = job;
            job.start();
        }
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
                // 实际还是join的实现
                synchronized (prev) {
                    while (prev.isAlive()) {
                        prev.wait(0);
                    }
                }
                log.info("... run");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
