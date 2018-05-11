package com.jdksource.proxy.jdk;

/**
 * @author zhangbo
 */
public class HelloService implements IHelloService, IHelloService2 {
    
    @Override
    public String hello(String name) {
        return "hello " + name;
    }

    @Override
    public String hello2(String name) {
        return "hello2 " + name;
    }
}
