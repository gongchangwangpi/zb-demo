package com.jdksource.proxy.jdk;

/**
 * @author zhangbo
 */
public interface IHelloService {
    
    String hello(String name);
    
    default void defaultSayHi() {
        System.out.println("IHelloService default say hi");
    }
}
