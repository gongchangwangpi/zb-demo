package com.jdksource.proxy.jdk;

/**
 * @author zhangbo
 */
public final class HelloService implements IHelloService, IHelloService2 {
    
    @Override
    public final String hello(String name) {
        return "hello " + name;
    }

    @Override
    public String hello2(String name) {
        return "hello2 " + name;
    }
    
    public void customHello() {
        System.out.println("HelloService custom hello");
    }
}
