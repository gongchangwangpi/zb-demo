package com.zb.springboot.demo.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @author zhangbo
 * @date 2020/5/20
 */
@RestController
public interface AsyncBaseController {

    @Async
    @GetMapping(value = "/async")
    Future<Long> asyncTest();

}
