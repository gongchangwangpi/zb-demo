package com.zb.springboot.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author bo6.zhang
 * @date 2021/7/2
 */
@RestController
@RequestMapping(value = "/http")
public class HttpController {

    @GetMapping(value = "/502")
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public String e502() {
        return "502";
    }

    @GetMapping(value = "/401")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String e401() {
        return "401";
    }

    @GetMapping(value = "/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String e404() {
        return "404";
    }

    @GetMapping(value = "/504")
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public String e504() {
        return "504";
    }

    @GetMapping(value = "/505")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String e500() {
        return "500";
    }

    @GetMapping(value = "/timeout")
    public String timeout() throws InterruptedException {
        TimeUnit.SECONDS.sleep(100);
        return "timeout";
    }

}
