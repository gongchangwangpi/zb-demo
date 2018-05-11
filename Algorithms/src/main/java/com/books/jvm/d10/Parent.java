package com.books.jvm.d10;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class Parent {

    static {
        System.out.println("Parent static");
    }

    public Parent() {
        System.out.println("Parent 构造器");
    }

    {
        System.out.println("Parent {}");
    }

    public void say() {
        System.out.println("Parent say");
    }
}
