package com.dubbo.test.spi;

/**
 * @author zhangbo
 */
public class SayHiImpl1 implements ISayHi {
    
    static {
        System.out.println("【static】 -- SayHiImpl1");
    }

    public SayHiImpl1() {
        System.out.println("【constructor】 ---- SayHiImpl1");
    }

    @Override
    public void say() {
        System.out.println("SAY Hi 1");
    }
}
