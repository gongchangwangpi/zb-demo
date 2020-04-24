package com.test.thread;

import com.books.bingfayishu.d4.SleepUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 3个线程，按顺序执行
 * join
 * 
 * @author zhangbo
 */
@Slf4j
public class ThreadRunInOrder4 {

    public static void main(String[] args) throws InterruptedException {
        Thread main = Thread.currentThread();

        MyThread t1 = new MyThread("t1");
        MyThread t2 = new MyThread("t2");
        MyThread t3 = new MyThread("t3");

        t1.start();
        // join的实现
        synchronized (t1) {
            while (t1.isAlive()) {
                t1.wait();
            }
        }
        t2.start();
        synchronized (t2) {
            while (t2.isAlive()) {
                t2.wait();
            }
        }
        t3.start();
        synchronized (t3) {
            while (t3.isAlive()) {
                t3.wait();
            }
        }
        
        log.info("------main ");
    }
    
    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            log.info("------- run ");
            SleepUtils.second(1);
        }
    }
    
}
