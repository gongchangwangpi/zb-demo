package com.jdksource.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2019-09-25
 */
@Slf4j
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("availableProcessors = " + Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 5; i++) {
            final int j = i;
            CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
                // 消费任务
                log.info(" === supplyAsync: {}", j);
                if (j < 2) {
                    return "Fail " + j;
                } else if (j < 4) {
                    throw new IllegalArgumentException("Error " + j);
                }
                return "OK " + j;
            }).thenApplyAsync((r) -> {
                // 根据第一步的结果进行消费
                log.info(" === thenApplyAsync: {}", r);
                if (r.startsWith("OK")) {
                    return "200";
                }
                return "500";
            }).whenCompleteAsync((result, throwable) -> {
                // 获取最后的Future
                if (throwable != null) {
                    log.error(" === whenCompleteAsync error", throwable);
                } else {
                    log.info(" === whenCompleteAsync get: {}", result);
                }
            });

        }

        TimeUnit.SECONDS.sleep(2);
    }

}
