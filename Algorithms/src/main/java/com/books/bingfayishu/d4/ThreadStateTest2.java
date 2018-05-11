package com.books.bingfayishu.d4;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ThreadStateTest2 {

    public static void main(String[] args) {
        
        new Thread(new Job()).start();
        new Thread(new Job()).start();
        
    }
    
    static class Job implements Runnable {

        @Override
        public void run() {
            t1();
        }
    }
    
    static synchronized void t1() {
        log.info("t1 --- sleep");
//        Thread.yield();
        SleepUtils.second(5);
        log.info("t1 --- wake up");
    }
}
