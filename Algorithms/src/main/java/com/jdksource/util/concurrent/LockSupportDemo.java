package com.jdksource.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 *
 * LockSupport
 *
 * @author zhangbo
 * @date 2020/12/2
 */
@Slf4j
public class LockSupportDemo {

    private static final AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws Exception {

        Thread aaa = new ThreadA();

//        TimeUnit.SECONDS.sleep(1);

        // unpark可以在park之前调用，但多次提前调用unpark，不会累计permit
        // 最终ThreadA只会执行一个循环
        LockSupport.unpark(aaa);
        LockSupport.unpark(aaa);
        LockSupport.unpark(aaa);
        LockSupport.unpark(aaa);
        LockSupport.unpark(aaa);
        log.info("main unpark five times");

        aaa.start();

    }

    @Slf4j
    private static class ThreadA extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("aaa run... {}", count.getAndIncrement());
                    LockSupport.park(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
