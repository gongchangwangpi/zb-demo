package com.books.bingfayishu.d5;

import java.util.concurrent.locks.Lock;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * 自定义TwinsLock测试
 *
 * @author zhangbo
 */
public class TwinsLockTest {

    public static void main(String[] args) {
        
        final Lock lock = new TwinsLock();
        
        class Worker extends Thread {
            public void run() {
                lock.lock();
                try {
                    SleepUtils.second(2);
                    System.out.println(Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }
        }
        
        // 启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.start();
        }
        
        // 每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }
    
}
