package com.test;

/**
 * 静态内部类 初始化时机
 *
 * Created by books on 2017/8/8.
 */
public class InnerStaticClzInitTest {

    public static void t() {
        System.out.println("out class t...");
    }

    private static class InnerStatic {

        {
            System.out.println("Inner Static class");
        }

        public static void t() {
            System.out.println("inner Static class t...");
        }
    }

    private class Inner {
        {
            System.out.println("Inner class >>>>>>>>>>>");
        }
    }

    public static void main(String[] args) {
        t();

//        InnerStatic.t();
    }

}
