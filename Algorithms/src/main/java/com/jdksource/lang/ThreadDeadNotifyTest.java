package com.jdksource.lang;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
@Slf4j
public class ThreadDeadNotifyTest {

    public static void main(String[] args) throws InterruptedException {

        Thread main = Thread.currentThread();
        
        new Thread(new Job(main), "testThread").start();

        TimeUnit.SECONDS.sleep(2);
        
        log.info("main dead");
    }
    
    @AllArgsConstructor
    static class Job implements Runnable {
        Thread thread;
        @Override
        public void run() {
            synchronized (thread) {
                log.info("synchronized wait");
                try {
                    thread.wait();
                    log.info("notify...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
