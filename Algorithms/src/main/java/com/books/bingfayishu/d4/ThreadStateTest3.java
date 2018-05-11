package com.books.bingfayishu.d4;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ThreadStateTest3 {
    
    static Object lock = new Object();

    public static void main(String[] args) {
        
        new Thread(new WaitJob()).start();
        
        SleepUtils.second(2);
        
        new Thread(new NotifyJob()).start();
        
    }
    
    static class WaitJob implements Runnable {

        @Override
        public void run() {
            synchronized(lock) {
                log.info("WaitJob --- wait");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SleepUtils.second(5);
                log.info("WaitJob --- wake up");
            }
            
        }
    }
    
    static class NotifyJob implements Runnable {

        @Override
        public void run() {
            synchronized(lock) {
                log.info("NotifyJob --- notify");
                
                lock.notify();
                
                SleepUtils.second(5);
                log.info("NotifyJob --- wake up");
            }
        }
    }
    
}
