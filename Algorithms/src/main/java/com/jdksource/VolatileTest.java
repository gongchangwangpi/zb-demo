package com.jdksource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * volatile 测试
 * 
 * Created by books on 2017/12/25.
 */
public class VolatileTest {
    
    volatile static int i = 0;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int j = 0; j < 10; j++) {
            executorService.execute(new VolatileUpdate());
        }
        
        executorService.shutdown();
    }
    
    static class VolatileUpdate implements Runnable {

        @Override
        public void run() {
            for (int j = 0; j < 1000; j++) {
//                i++;
                i = j;
                
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(i);
        }
    }
}
