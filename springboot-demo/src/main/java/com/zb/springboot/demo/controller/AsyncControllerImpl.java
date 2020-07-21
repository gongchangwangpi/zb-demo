package com.zb.springboot.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangbo
 * @date 2020/5/20
 */
@Slf4j
@RestController
public class AsyncControllerImpl implements AsyncBaseController {

    @Override
    public Future<Long> asyncTest() {
        log.info("=====  async controller =====");
        return new Future<Long>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Long get() throws InterruptedException, ExecutionException {
                return System.currentTimeMillis();
            }

            @Override
            public Long get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return System.currentTimeMillis();
            }
        };
    }
}
