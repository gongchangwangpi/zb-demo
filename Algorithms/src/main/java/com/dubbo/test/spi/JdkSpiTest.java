package com.dubbo.test.spi;

import java.util.ServiceLoader;

/**
 * @author zhangbo
 */
public class JdkSpiTest {

    public static void main(String[] args) throws InterruptedException {
        // 类加载一次，每次执行load时，都会在调用一次构造器初始化实例
        ServiceLoader<ISayHi> serviceLoader = ServiceLoader.load(ISayHi.class);
        serviceLoader.forEach(ISayHi::say);
        
        System.out.println("---------------------------------");
        
        serviceLoader = ServiceLoader.load(ISayHi.class);
        serviceLoader.forEach(ISayHi::say);

        
    }
    
}
