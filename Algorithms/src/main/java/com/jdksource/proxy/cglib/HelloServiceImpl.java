package com.jdksource.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public /*final*/ class HelloServiceImpl implements HelloService {
    
    @Override
    public /*final*/ String hello(String name) {
        log.info("----hello: {}", name);
        return "hello: " + name;
    }
    
}
