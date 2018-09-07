package com.jdksource.util.concurrent;

import com.books.bingfayishu.d4.SleepUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * jdk默认的{@link Executors#newCachedThreadPool},使用了同步队列{@link SynchronousQueue}
 * 和{@link Integer#MAX_VALUE}作为线程池的最大线程数，此时队列中不会堆积任务。新提交一个任务，
 * 都会找空闲线程或者新建线程来执行任务
 * 
 * @author zhangbo
 */
public class CachedThreadPoolTest {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    public static void main(String[] args) {

//        ExecutorService executorService = Executors.newCachedThreadPool();
        
        ExecutorService executorService = new ThreadPoolExecutor(2, 8,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(20));

        for (int i = 0; i < 4; i++) {
            executorService.execute(new Job(i));
        }
     
        executorService.shutdown();

//        System.out.println(COUNT_BITS);
//        System.out.println(CAPACITY);
//        System.out.println(RUNNING);
//        System.out.println(SHUTDOWN);
//        System.out.println(STOP);
//        System.out.println(TIDYING);
//        System.out.println(TERMINATED);
//        System.out.println(RUNNING | 5);
//        System.out.println((RUNNING | 5) & CAPACITY);
//        System.out.println((RUNNING | 5) & ~CAPACITY);
        
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
            log.info("----- run, after sleep {}", i);
        }
    }
    
}
