package com.jdksource.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2018/8/28 0028.
 */
public class UncaughtExceptionHandlerTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new Job());
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        thread.start();
    }

    @Slf4j
    static class Job implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                log.info("i = {}", i);
                if (i == 5) {
                    RuntimeException e = new RuntimeException("runtime exception: i = " + i);
                    log.warn("----------- ", e.hashCode());
                    throw e;
                }
            }
        }
    }

    @Slf4j
    static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("thread : {}", t == Thread.currentThread());
            log.error("uncaught exception: ", e.hashCode());
            log.error("uncaught exception: ", e.getLocalizedMessage());
        }
    }
}
