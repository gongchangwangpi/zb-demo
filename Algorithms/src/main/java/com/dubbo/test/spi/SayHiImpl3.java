package com.dubbo.test.spi;

/**
 * @author zhangbo
 */
public class SayHiImpl3 implements ISayHi {

    static {
        System.out.println("【static】 -- SayHiImpl3");
    }

    public SayHiImpl3() {
        System.out.println("【constructor】 ---- SayHiImpl3");
    }
    
    @Override
    public void say() {
        System.out.println("SAY Hi 3");
    }
}
