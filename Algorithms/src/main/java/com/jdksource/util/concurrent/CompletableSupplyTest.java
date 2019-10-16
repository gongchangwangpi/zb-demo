package com.jdksource.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author zhangbo
 * @date 2019-09-25
 */
@Slf4j
public class CompletableSupplyTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("availableProcessors = " + Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 5; i++) {
            CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
                log.info(" --- ");
                return "OK";
            });

            String s = stringCompletableFuture.get();
            log.info(s);
        }


    }

}
