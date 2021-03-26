package com.jdksource.util.concurrent;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在第一次执行拒绝策略时，将max size增长一倍
 *
 * @author zhangbo
 * @date 2020/12/26
 */
@Slf4j
public class ThreadPoolRejectPolicyAutoIncreMaxPoolSizeTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2), new DefaultThreadFactory("incre"), new IncrementMaxSizeRejectPolicy());

        for (int i = 0; i < 10; i++) {
            final int j = i;
            executor.execute(() -> {
                log.info(" run === {}", j);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    //
                }
            });
        }

        executor.shutdown();
    }

    /**
     * 自定义拒绝策略，在首次执行拒绝策略时，将max size增长一倍。
     * 因为在业务初期，通常不是能很好的预估业务量，所以执行该策略，给予一定的弹性空间
     */
    @Slf4j
    private static class IncrementMaxSizeRejectPolicy implements RejectedExecutionHandler {

        private static volatile boolean increment = false;

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int maximumPoolSize = executor.getMaximumPoolSize();
            if (!increment) {
                increMaxSize(r, executor, maximumPoolSize);
            } else {
                log.info("reject task: {}", r);
                // 继续其他的策略，如log, callRunner, discard task, throw exception, save in db等
                throw new RejectedExecutionException();
            }
        }

        private synchronized void increMaxSize(Runnable r, ThreadPoolExecutor executor, int maximumPoolSize) {
            if (!increment) {
                increment = true;
                log.info("incre max size: {}", maximumPoolSize);
                executor.setMaximumPoolSize(maximumPoolSize * 2);
            }
            executor.execute(r);
        }
    }

    @Slf4j
    private static class SaveInDbRejectPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 先保存到DB，同时记录日志，发送告警邮件等，等待后续空闲下来，在进行人工手动消费
            log.warn("线程池超过负载...");
//            save2DB(r);
//            sendAlarmEmail();
        }
    }

}
