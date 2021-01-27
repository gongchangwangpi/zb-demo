package com.zb.springboot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author bo6.zhang
 * @date 2021/1/26
 */
@RestController
public class SleepController {

    @GetMapping(value = "/sleep/{s}")
    public Date sleep(@PathVariable("s") Long s) throws InterruptedException {
        TimeUnit.SECONDS.sleep(s);
        return new Date();
    }

}
