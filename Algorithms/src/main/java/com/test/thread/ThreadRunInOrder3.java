package com.test.thread;

import com.books.bingfayishu.d4.SleepUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 3个线程，按顺序执行
 * 
 * @author zhangbo
 */
@Slf4j
public class ThreadRunInOrder3 {

    public static void main(String[] args) throws InterruptedException {

        MyThread t1 = new MyThread("t1");
        MyThread t2 = new MyThread("t2");
        MyThread t3 = new MyThread("t3");

        // 最终是一个线程来执行，通过LinkedBlockingQueue来排队
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        
        executorService.execute(t1);
        executorService.execute(t2);
        executorService.execute(t3);
        
        executorService.shutdown();
    }
    
    static class MyThread extends Thread {
        String name;
        public MyThread(String name) {
            super(name);
            this.name = name;
        }
        @Override
        public void run() {
            log.info("------- run {}", name);
            SleepUtils.second(1);
        }
    }
    
}
