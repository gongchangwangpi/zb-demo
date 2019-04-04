package com.dubbo.test.spi.jdk;

import com.dubbo.test.spi.ISayHi;

/**
 * @author zhangbo
 */
public class JdkSayHiImpl1 implements ISayHi {
    
    @Override
    public void say() {
        System.out.println("JDK SAY Hi 1");
    }
}
