package com.test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用【单线程无锁/单线程有锁/单线CAS/多线程有锁】的情况，
 * 分别针对64位的long类型的自增5亿次，比较各自的时间
 * 
 * @author zhangbo
 */
@Slf4j
public class LockCasTest {
    
    private static Lock lock = new ReentrantLock();
    private static int count = 500000000;
    
    public static void main(String[] args) {
        
        singleThreadNoLock(); // 20ms
        singleThreadLock(); // 8777ms
        singleThreadCas(); // 2692ms
        multiThreadsLock(); // 26128ms
        
    }
    
    private static void singleThreadNoLock() {
        long l = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            l++;
        }
        long end = System.currentTimeMillis();
        log.info("------ {}, singleThreadNoLock use time: {}", l, end - start);
    }
    
    private static void singleThreadLock() {
        long l = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            lock.lock();
            try {
                l++;
            } finally {
                lock.unlock();
            }
        }
        long end = System.currentTimeMillis();
        log.info("------ {}, singleThreadLock use time: {}", l, end - start);
    }
    
    private static void singleThreadCas() {
        AtomicLong l = new AtomicLong(0);
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            l.incrementAndGet();
        }
        long end = System.currentTimeMillis();
        log.info("------ {}, singleThreadCas use time: {}", l, end - start);
    }

    static long staticL = 0;
    private static void multiThreadsLock() {
        
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < count / 2; i++) {
                lock.lock();
                try {
                    staticL++;
                } finally {
                    lock.unlock();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < count / 2; i++) {
                lock.lock();
                try {
                    staticL++;
                } finally {
                    lock.unlock();
                }
            }
        });
        
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("------ {}, multiThreadsLock use time: {}", staticL, end - start);
    }
    
}
