package com.test.thread;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 3个线程，按顺序执行
 * 
 * @author zhangbo
 */
@Slf4j
public class ThreadRunInOrder {

    public static void main(String[] args) {
        Thread main = Thread.currentThread();

        MyThread t1 = new MyThread(main, "t1");
        MyThread t2 = new MyThread(t1, "t2");
        MyThread t3 = new MyThread(t2, "t3");

        t1.start();
        t2.start();
        t3.start();
        
        log.info("------main ");
        SleepUtils.second(1);
        log.info("------main ");
    }
    
    static class MyThread extends Thread {
        Thread t;

        public MyThread(Thread t, String name) {
            super(name);
            this.t = t;
        }

        @Override
        public void run() {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("------- run ");
            SleepUtils.second(1);
        }
    }
    
}
