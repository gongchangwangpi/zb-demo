package com.books.gaobingfa.d2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by books on 2017/4/17.
 * */
@Slf4j
public class ThreadInterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    //
                    log.error("--- InterruptedException");
                    // 线程阻塞在sleep等方法上，仅会收到InterruptedException异常，不会设置interrupt标记：false
                    log.error("isInterrupted = {}", Thread.currentThread().isInterrupted());
                }
            }
        };

        t1.start();
        TimeUnit.MILLISECONDS.sleep(10);
        t1.interrupt();
    }
}