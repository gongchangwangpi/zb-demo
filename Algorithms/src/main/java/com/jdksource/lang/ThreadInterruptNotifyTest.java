package com.jdksource.lang;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 线程在wait时，可以被打断唤醒，但打断后不是立即唤醒
 * 还需要等待重新获取到synchronized对应的锁才能唤醒
 * 
 * @author zhangbo
 */
@Slf4j
public class ThreadInterruptNotifyTest {
    
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread main = Thread.currentThread();

        Thread testThread = new Thread(new Job(), "testThread");
        testThread.start();
        // 保证testThread先进入同步块
        TimeUnit.MILLISECONDS.sleep(50);
        
        Thread sleepThread = new Thread(new Job2(), "sleepThread");
        sleepThread.start();

        TimeUnit.SECONDS.sleep(1);

        log.info("testThread interrupt");
        testThread.interrupt();
        
        TimeUnit.SECONDS.sleep(1);
        log.info("main dead");
    }
    
    @AllArgsConstructor
    static class Job implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                log.info(" === synchronized wait === ");
                try {
                    lock.wait();
                    log.info(" === normal notify === ");
                } catch (InterruptedException e) {
                    log.error(" === Job InterruptedException === ");
                    log.info(" === exception notify... === ");
                }
                log.info(" === Job dead... === ");
            }
        }
    }
    
    @AllArgsConstructor
    static class Job2 implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                log.info(" --- Job2 synchronized sleep --- ");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    log.error(" --- Job InterruptedException --- ");
                }
                log.info(" --- Job2 dead --- ");
            }
        }
    }
}
