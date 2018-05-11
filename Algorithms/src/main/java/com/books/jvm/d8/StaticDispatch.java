package com.books.jvm.d8;

/**
 * @author zhangbo
 */
public class StaticDispatch {

    public static void main(String[] args) {
        StaticDispatch staticDispatch = new StaticDispatch();
        T t = new T();
        T1 t1 = new T1();
        T2 t2 = new T2();
        
        staticDispatch.hello(t);
        staticDispatch.hello(t1);
        staticDispatch.hello(t2);

        t = t1;
        staticDispatch.hello(t);
    }
    
    public void hello(T t) {
        System.out.println("T");
    }
    public void hello(T1 t) {
        System.out.println("T1");
    }
    public void hello(T2 t) {
        System.out.println("T2");
    }
    
    static class T {
        
    }
    static class T1 extends T {
        
    }
    static class T2 extends T {
        
    }
}
