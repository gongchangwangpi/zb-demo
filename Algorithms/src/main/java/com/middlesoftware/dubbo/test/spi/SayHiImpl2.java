package com.middlesoftware.dubbo.test.spi;

/**
 * @author zhangbo
 */
public class SayHiImpl2 implements ISayHi {

    static {
        System.out.println("【static】 -- SayHiImpl2");
    }

    public SayHiImpl2() {
        System.out.println("【constructor】 ---- SayHiImpl2");
    }
    
    @Override
    public void say() {
        System.out.println("SAY Hi 2");
    }
}
