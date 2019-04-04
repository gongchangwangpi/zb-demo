package com.dubbo.test.spi.jdk;

import com.dubbo.test.spi.ISayHi;

import java.util.ServiceLoader;

/**
 * @author zhangbo
 */
public class JdkSpiTest {

    public static void main(String[] args) {
        ServiceLoader<ISayHi> serviceLoader = ServiceLoader.load(ISayHi.class);
        serviceLoader.forEach(ISayHi::say);
        
        serviceLoader = ServiceLoader.load(ISayHi.class);
        serviceLoader.forEach(ISayHi::say);
    }
    
}
