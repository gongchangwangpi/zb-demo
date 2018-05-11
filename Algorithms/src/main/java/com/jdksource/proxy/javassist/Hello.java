package com.jdksource.proxy.javassist;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class Hello {
    
    public String hello(String name) {
        log.info(name);
        return "hello: " + name;
    }
    
}
