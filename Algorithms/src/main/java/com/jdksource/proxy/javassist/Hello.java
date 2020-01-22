package com.jdksource.proxy.javassist;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class Hello {
    
    public String hello(String name) {
        log.info("slf4j: {}", name);
        return "hello: " + name;
    }
    
}
