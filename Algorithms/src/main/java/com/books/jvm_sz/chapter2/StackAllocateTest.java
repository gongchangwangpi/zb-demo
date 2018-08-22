package com.books.jvm_sz.chapter2;

/**
 * 使用栈上分配技术，使某些线程私有的对象(不会逃逸)不在堆上分配，避免GC
 * 只分配10M的堆内存，开启逃逸分析，开启GC打印，关闭TLAB，允许栈上分配
 * 
 * -Xmx10M -Xms10M -XX:+PrintGC -XX:+DoEscapeAnalysis -XX:-UseTLAB -XX:+EliminateAllocations
 * 
 * 循环1亿次，会分配1.5大小的内存，实验失败。。。
 * 
 * @author zhangbo
 */
public class StackAllocateTest {

    public static void main(String[] args) {

        long s = System.currentTimeMillis();

        for (int i = 0; i < 100_000_000; i++) {
            allocate();
        }
        
        long e = System.currentTimeMillis();

        System.out.println(e - s);
    }
    
    private static void allocate() {
        User user = new User();
    }
    
    static class User {
        private int id = 1;
        private String name = "name";
    }
}
