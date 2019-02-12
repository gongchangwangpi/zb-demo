package com.jdksource.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 *
 * Created by books on 2017/4/10.
 */
public class ReentrantLockInterruptTest1 {

    private static ReentrantLock lock = new ReentrantLock();
    
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Job(), "1111");
        Thread t2 = new Thread(new Job(), "2222");
        
        t1.start();
        TimeUnit.MILLISECONDS.sleep(50);
        
        t2.start();

        // 此时打断，由于该线程已经被park，所以不会立即抛出InterruptedException
        // 而是等待线程重新唤醒后，在由AbstractQueuedSynchronizer#selfInterrupt()
        t2.interrupt();

    }

    @Slf4j
    static class Job implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                log.info("get lock");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                log.error("打断", e);
            } finally {
                lock.unlock();
            }
        }
    }
    
}
