package com.jdksource.util;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * Created by books on 2017/4/1.
 */
@Slf4j
public class CallableTest {

    public static void main(String[] args) throws Exception {

        Object call = new MyCallable().call();

        new MyRunnable().run();

        new MyThread().start();

        System.out.println(call);
    }

    static class MyCallable implements Callable {

        @Override
        public Object call() throws Exception {
            log.info("callable call");

            TimeUnit.SECONDS.sleep(5);

            return "call";
        }
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            log.info("runnable run");
        }
    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            log.info("thread run");
        }
    }
}
