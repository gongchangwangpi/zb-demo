package com.springapp.maidian;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Slf4j
@Controller
public class UserController {

    @Track(application = "user", eventType = "register")
    public String register(String mobile, String username) {
        log.info("register === username = {}, mobile = {}", username, mobile);
        return "OK";
    }

    @Track(application = "user", eventType = "get")
    public String get() {
        log.info("get === ");
        return "Get OK";
    }

    @Track(application = "user", eventType = "error")
    public String error() {
        log.info("error === ");
        int i = 1 / 0;
        return "Get OK";
    }

}
