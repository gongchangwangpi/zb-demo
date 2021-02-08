package com.guava.common.util;

import com.books.bingfayishu.d4.SleepUtils;
import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.concurrent.*;

/**
 * 替代Future，可以添加回调函数，不用再{@link java.util.concurrent.Future#get()}时阻塞等待
 * 
 * @author zhangbo
 */
@Slf4j
public class ListenableFutureCallbackTest {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 装饰java原生线程池
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        // 提交任务
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(new Job());

        // 包装为超时的Future.get(timeout)
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        ListenableFuture<String> timeoutListenableFuture = Futures.withTimeout(listenableFuture, Duration.ofSeconds(2), scheduledExecutorService);

        // 添加callback，此处还可以将callback的线程池和任务线程池隔离
        Futures.addCallback(timeoutListenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                log.info(" === callback success: {}", result);
            }

            @Override
            public void onFailure(Throwable t) {
                log.error(" === callback error", t);
            }
        }, executorService);

        log.info(" === add listener");

        // 不能先shutdown，在任务callback的时候，如果线程池shutdown，会报拒绝执行的异常
        TimeUnit.SECONDS.sleep(5);
        scheduledExecutorService.shutdown();
        executorService.shutdown();
    }
    
    @Slf4j
    private static class Job implements Callable<String> {

        @Override
        public String call() {
            log.info("---->>> call start: sleep 2 seconds");
            SleepUtils.second(2);
            log.info("---->>> call end");
            return "123456";
        }
    }

}
