package com.jdksource.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 优雅关闭线程池
 *
 * @author zhangbo
 * @date 2020/3/25
 **/
@Slf4j
public class GraceShutdownThreadPool {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 5; i++) {
            TimeUnit.MILLISECONDS.sleep(100);
            final int j = i;
            executorService.submit(() -> {
                try {
                    // 模拟耗时任务
                    TimeUnit.SECONDS.sleep(2);
                    log.info(" run >>> {}", j);
                } catch (InterruptedException e) {
                    log.error("interrupted : ", e);
                }
            });
        }

        executorService.shutdown();
        log.info("shutdown");

        // 阻塞一段时间，如果线程池还没有关闭，则强制关闭
        if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
            log.warn("强制关闭线程池");
            executorService.shutdownNow();
        }
        log.info(" main end");
    }

}
