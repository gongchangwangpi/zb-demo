package com.test.ordercode;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangbo
 */
public class GenerateOrderCode {
    
    private static ReentrantLock lock = new ReentrantLock();
    
    private static AtomicLong code = new AtomicLong(1000L);

    public static String lockByTime() {
        lock.lock();
        String orderCode;
        try {
            orderCode = System.nanoTime() + RandomStringUtils.randomNumeric(5);
        } finally {
            lock.unlock();
        }
        return orderCode;
    }
    
    public static synchronized String syncByTime() {
        return System.nanoTime() + RandomStringUtils.randomNumeric(5);
    }

    public static long orderCodeByAtomicLong() {
        return code.getAndIncrement();
    }

    public static long orderCodeByAtomicLong(long delta) {
        return code.getAndAdd(delta);
    }
    
}
