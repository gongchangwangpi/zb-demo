package com.zb.springboot.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2019-12-02
 */
@Slf4j
@RestController
@RequestMapping(value = "DeferredResult")
public class DeferredResultController {

    private static final ResponseEntity<Date> NOT_MODIFIED_RESPONSE = new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);

    // 设置超时时间和默认返回结果
    private DeferredResult<ResponseEntity<Date>> dateDeferredResult = new DeferredResult<>(10000L);

    @GetMapping
    public DeferredResult<ResponseEntity<Date>> get() {
        dateDeferredResult.onTimeout(() -> {
            log.warn("get timeout ...");
        });
        return dateDeferredResult;
    }

    @GetMapping(value = "/set")
    public String set() {

        Date date = new Date();
        log.info("set DeferredResult result, date = {}", date);

        dateDeferredResult.setResult(new ResponseEntity<>(date, HttpStatus.OK));

        return "OK";
    }


    @GetMapping(value = "/{time}")
    public DeferredResult<ResponseEntity<Date>> deferredResult(@PathVariable("time") int time) throws InterruptedException {

        log.info("DeferredResult Test, time = {}", time);

        // 设置超时时间和默认返回结果
        DeferredResult<ResponseEntity<Date>> dateDeferredResult = new DeferredResult<>(1000L, NOT_MODIFIED_RESPONSE);

        dateDeferredResult.onTimeout(() -> {
            log.warn("timeout ...");
        });

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Date date = new Date();
            log.info("DeferredResult result, date = {}", date);

            dateDeferredResult.setResult(new ResponseEntity<>(date, HttpStatus.OK));
        }, "DeferredResultThread").start();

        return dateDeferredResult;
    }

}
