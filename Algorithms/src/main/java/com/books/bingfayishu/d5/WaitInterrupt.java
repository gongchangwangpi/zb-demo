package com.books.bingfayishu.d5;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * @author zhangbo
 */
public class WaitInterrupt {
    
    private static Object lock = new Object();

    public static void main(String[] args) {
        Thread thread = new Thread(new Thread1(), "test");
        
        thread.start();

        SleepUtils.second(3);
        
        thread.interrupt();
    }
    
    static class Thread1 implements Runnable {

        @Override
        public void run() {
            System.out.println("run...");
            SleepUtils.second(1);
            synchronized (lock) {
                try {
//                    Thread.currentThread().interrupt();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
