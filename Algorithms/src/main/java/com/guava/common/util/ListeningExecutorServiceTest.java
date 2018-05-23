package com.guava.common.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.books.bingfayishu.d4.SleepUtils;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import lombok.extern.slf4j.Slf4j;

/**
 * 替代Future，可以添加回调函数，不用再{@link java.util.concurrent.Future#get()}时阻塞等待
 * 
 * @author zhangbo
 */
public class ListeningExecutorServiceTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ListeningExecutorService listeningExecutorService = MoreExecutors.newDirectExecutorService();

        ListenableFuture<?> listenableFuture = listeningExecutorService.submit(new Job());

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        listenableFuture.addListener(new Callback(listenableFuture.get()), executorService);

        executorService.shutdown();
    }
    
    @Slf4j
    private static class Job implements Callable<String> {

        @Override
        public String call() {
            log.info("---->>> sleep 2 seconds");
            SleepUtils.second(2);
            log.info("---->>> {}", Thread.currentThread());  
            return "123456";
        }
    }
    
    @Slf4j
    private static class Callback implements Runnable {
        private Object result;

        public Callback(Object result) {
            this.result = result;
        }

        @Override
        public void run() {
            log.info("------- future callback: {}", result);
        }
    }
}
