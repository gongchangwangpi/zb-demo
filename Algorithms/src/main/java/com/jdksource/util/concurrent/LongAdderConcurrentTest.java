package com.jdksource.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 多线程环境下 LongAdder 与 AtomicLong 的比较
 * 
 * 增加到1亿时，两个用时差不多，和书上的不符，
 * 不过 AtomicLong 分别在JDK1.7 (5000毫秒) 和 1.8 (2000毫秒) 用时差异明显
 * 
 * @author zhangbo
 */
public class LongAdderConcurrentTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static final long TARGET_VALUE = 100_000_000L;
    private static final int THREAD_COUNT = 16;
    
    public static void main(String[] args) {
        // ----- LongAdder ----- // 
        /*LongAdder longAdder = new LongAdder();
        long s1 = System.currentTimeMillis();
        
        ExecutorService adderThreadPool = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            adderThreadPool.execute(new LongAdderJob(longAdder, s1));
        }

        SleepUtils.second(5);*/

        // ----- AtomicLong ----- // 
        AtomicLong atomicLong = new AtomicLong();
        long s2 = System.currentTimeMillis();
        
        ExecutorService atomicThreadPool = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            atomicThreadPool.execute(new AtomicLongJob(atomicLong, s2));
        }
        
//        adderThreadPool.shutdown();
        atomicThreadPool.shutdown();
    }
    
    /*static class LongAdderJob implements Runnable {

        LongAdder longAdder;
        long startMillis;

        public LongAdderJob(LongAdder longAdder, long startMillis) {
            this.longAdder = longAdder;
            this.startMillis = startMillis;
        }

        @Override
        public void run() {

            while (longAdder.longValue() <= TARGET_VALUE) {
                longAdder.increment();
            }

            long e1 = System.currentTimeMillis();
            System.out.println("LongAdder value = " + longAdder.longValue() + ", use time " + (e1 - startMillis));
        }
    }*/
    
    static class AtomicLongJob implements Runnable {
        AtomicLong atomicLong;
        long startMillis;

        public AtomicLongJob(AtomicLong atomicLong, long startMillis) {
            this.atomicLong = atomicLong;
            this.startMillis = startMillis;
        }

        @Override
        public void run() {

            while (atomicLong.getAndIncrement() <= TARGET_VALUE) {
            }

            long e1 = System.currentTimeMillis();
            System.out.println("AtomicLong value = " + atomicLong + ", use time " + (e1 - startMillis));
        }
    }
}
