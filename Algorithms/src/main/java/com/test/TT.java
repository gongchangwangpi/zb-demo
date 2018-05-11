package com.test;

/**
 * Created by books on 2017/5/2.
 */
public class TT extends T {
    public static final String STRING = "TT.string";

    {
        System.out.println("tt");
    }
    static {
        System.out.println("static tt");
    }
}
