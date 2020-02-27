package com.zb.apollo.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 * @date 2019-12-14
 */
@Slf4j
@RestController
public class TestController {

    @Value("{testValue:default}")
    private String testValue;

    @GetMapping(value = "/test")
    public Object test() {
        return testValue;
    }

}
