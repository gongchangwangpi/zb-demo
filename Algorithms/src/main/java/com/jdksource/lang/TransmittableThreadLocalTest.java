package com.jdksource.lang;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TransmittableThreadLocal
 *
 * @author zhangbo
 * @date 2020/9/21
 */
@Slf4j
public class TransmittableThreadLocalTest {

//    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
//    private static TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        threadLocal.set("main");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                log.info(threadLocal.get());
                threadLocal.remove();
            });
        }

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            log.info("test run =====");
            threadLocal.set("test");
        }, "test").start();

        TimeUnit.SECONDS.sleep(1);

        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                log.info(threadLocal.get());
            });
        }

        executorService.shutdown();
    }

}
