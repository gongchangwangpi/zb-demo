package com.zb.springboot.demo.controller;

import com.zb.springboot.demo.track.Track;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserTrackTestController {

    @Track(application = "user", eventType = "register")
    @GetMapping(value = "/register")
    public String register(String mobile, String username) {
        log.info("register === username = {}, mobile = {}", username, mobile);
        return "OK";
    }

    @Track(application = "user", eventType = "get")
//    @GetMapping(value = "/get/{id}")
    @RequestMapping(value = "/get/{id}")
    public String get(@PathVariable("id") Long id) {
        log.info("get === {}", id);
        return "Get OK";
    }

    @Track(application = "user", eventType = "error")
    @GetMapping(value = "/error")
    public String error() {
        log.info("error === ");
        int i = 1 / 0;
        return "Get OK";
    }

}
