package com.middlesoftware.dubbo.consumer.mock;

import com.middlesoftware.dubbo.api.HelloService;

import lombok.extern.slf4j.Slf4j;

/**
 * 在消费方设置mock后，如果在调用服务方失败后，将调用此mock方法进行服务降级
 * 
 * @author zhangbo
 */
@Slf4j
public class HelloServiceMock implements HelloService {
    
    @Override
    public String hello(String name) {
        log.warn("mock ---- hello: {}", name);
        return name;
    }
}
