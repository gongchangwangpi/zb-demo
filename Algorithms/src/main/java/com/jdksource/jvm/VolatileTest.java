package com.jdksource.jvm;

/**
 * @author zhangbo
 * @date 2020-01-22
 */
public class VolatileTest {

    private static volatile int i = 0;

    public static void main(String[] args) {
        i = 3;
        System.out.println("----- test -----");
        System.out.println(i);
    }

}
