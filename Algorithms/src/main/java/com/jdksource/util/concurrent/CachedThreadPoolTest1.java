package com.jdksource.util.concurrent;

import com.books.bingfayishu.d4.SleepUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * jdk默认的{@link Executors#newCachedThreadPool},使用了同步队列{@link SynchronousQueue}
 * 和{@link Integer#MAX_VALUE}作为线程池的最大线程数，此时队列中不会堆积任务。新提交一个任务，
 * 都会找空闲线程或者新建线程来执行任务
 * 
 * 一般情况下，可自己新建线程池对象，自行调整corePoolSize,MaxPoolSize,workQueue等。
 * 
 * @author zhangbo
 */
public class CachedThreadPoolTest1 {


    public static void main(String[] args) {

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 8,
                1L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        executorService.allowCoreThreadTimeOut(false);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new Job(i));
//            if (i % 15 == 0) {
                SleepUtils.second(3);
                System.out.println("-----------------------------------------------");
//            }
        }
     
        executorService.shutdown();
        
    }
    
    @Slf4j
    @Getter
    static class Job implements Runnable {
        private int i;

        public Job(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            log.info("----- run, before sleep {}", i);
            SleepUtils.second(2);
//            log.info("----- run, after sleep {}", i);
        }
    }
    
}
