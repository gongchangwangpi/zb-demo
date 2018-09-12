package com.jdksource.lang;

/**
 * @author zhangbo
 */
public class ClassInitTest {

    static {
        System.out.println("static");
    }
    {
        System.out.println("code block");
    }
    public ClassInitTest() {
        System.out.println("constructor");
    }
    
}
