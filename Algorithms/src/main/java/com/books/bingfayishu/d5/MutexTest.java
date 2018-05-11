package com.books.bingfayishu.d5;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * @author zhangbo
 */
public class MutexTest {

    private static Mutex mutex = new Mutex();
    
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            Worker worker = new Worker();
            
            worker.start();

        }
        
    }
    
    
    static class Worker extends Thread {
        public void run() {
            mutex.lock();
            try {
                SleepUtils.second(2);
                System.out.println(Thread.currentThread().getName());
                // 同一个线程在获取该互斥锁后，如果再次获取该锁，会被阻塞，不支持可重入
                mutex.lock();
            } finally {
                mutex.unlock();
            }
        }
    }
}
