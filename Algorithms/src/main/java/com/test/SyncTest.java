package com.test;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class SyncTest implements Runnable {

    int b = 100;
    
    public static void main(String[] args) {
        SyncTest tt = new SyncTest();
        Thread t = new Thread(tt);
        t.start();
        tt.m2();
        System.out.println("main thread b = " + tt.b);
    }
    
    public synchronized void m1() {
        b = 1000;
        SleepUtils.millis(500);
        System.out.println("b = " + b);
    }
    
    public synchronized void m2() {
        SleepUtils.millis(250);
        b = 2000;
    }
    
    @Override
    public void run() {
        log.info("{}", this);
        m1();
    }
}
