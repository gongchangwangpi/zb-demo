package com.books.jvm.d7;

/**
 * -XX:+TraceClassLoading
 * 
 * @author zhangbo
 */
public class ClassLoadTest {

    public static void main(String[] args) {
        // T1 T2 都会被加载
        new T1().m();
    }
    
    static class T1 {
        public void m() {
            new T2().m();
        }
    }
    static class T2 {
        public void m() {
            
        }
    }
}
