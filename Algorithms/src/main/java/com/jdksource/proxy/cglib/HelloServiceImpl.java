package com.jdksource.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

/**
 * 自己使用cglib生成的代理类，里面使用this获取的是生成的代理子类
 * 而在spring容器中，使用this获取却是该类本身，导致cglib的事务同样不能生效
 *
 *
 * @author zhangbo
 */
@Slf4j
public /*final*/ class HelloServiceImpl implements HelloService {
    
    @Override
    public /*final*/ String hello(String name) {
        log.info("----super this: {}", this);
        log.info("----super this.getClass: {}", this.getClass());
        log.info("----super hello: {}", name);
        this.say();
        return "super hello: " + name;
    }

    @Override
    public String say() {
        log.info("----super this: {}", this);
        log.info("----super this.getClass: {}", this.getClass());
        log.info("----super say");
        return "super say";
    }

}
