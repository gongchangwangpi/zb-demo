package com.books.jvm_sz.chapter10;

/**
 * -XX:+TraceClassLoading
 * 
 * @author zhangbo
 */
public class ClassLoadingTest {

    public static void main(String[] args) {
//        System.out.println(Parent.s1);
//        System.out.println(Parent.s2);
//        System.out.println(Son.s1);
        System.out.println(Son.s2);
    }
    
    static class Parent {
        static String s1 = "parent static s1";
        static final String s2 = "parent static final s2";
        static {
            System.out.println("Parent class loaded and init");
        }
    }
    static class Son extends Parent {
        static String s1 = "son static s1";
        static {
            System.out.println("Son class loaded and init");
        }
    }
}
