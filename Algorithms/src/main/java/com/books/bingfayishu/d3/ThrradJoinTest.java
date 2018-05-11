package com.books.bingfayishu.d3;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class ThrradJoinTest {

    public static void main(String[] args) throws Exception {

        Thread1 thread1 = new Thread1();
        
        thread1.start();
        thread1.join();

        System.out.println("main");
    }
    
    static class Thread1 extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("Thread1 sleep");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Thread1 await");
            } catch (InterruptedException e) {
                
            }
        }
    }
}
