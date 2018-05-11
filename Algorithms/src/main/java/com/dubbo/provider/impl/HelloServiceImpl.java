package com.dubbo.provider.impl;

import com.dubbo.api.HelloService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class HelloServiceImpl implements HelloService {
    
    @Override
    public String hello(String name) {
        log.info("hello : {}", name);
        return name;
    }
}
