package com.books.jvm.d10;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class Son extends Parent{

    public Son() {
        System.out.println("Son 构造器");
    }

    static {
        System.out.println("Son static");
    }

    {
        System.out.println("Son {}");
    }

    public void say() {
        System.out.println("Son say");
    }

}
