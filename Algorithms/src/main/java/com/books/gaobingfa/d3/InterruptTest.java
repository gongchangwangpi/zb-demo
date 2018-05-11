package com.books.gaobingfa.d3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link Thread#interrupt()}
 * 
 * @author zhangbo
 */
@Slf4j
public class InterruptTest {
    
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner());
        t1.start();
        
        SleepUtils.second(1);
        
        Thread t2 = new Thread(new Runner());
        t2.start();

        SleepUtils.second(1);
        
        t2.interrupt();
        log.info("t2 interrupt");

    }
    
    static class Runner implements Runnable {

        @Override
        public void run() {
            // InterruptedException: sleep interrupted
//            SleepUtils.second(5);
            
            // 无异常，相当于唤醒了线程，继续执行后面的代码
//            LockSupport.park();

            lock.lock();
            try {
                log.info("run");
                SleepUtils.second(5);
            } finally {
                lock.unlock();
            }
        }
    }
    
}
