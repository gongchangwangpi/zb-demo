package com.books.jvm.d7;


/**
 * <clinit>()由JVM保证只能有一个线程去执行初始化
 *
 * Created by Administrator on 2017/5/2 0002.
 */
public class DeadLoopClassTest {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + " end");
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
    }
    static class DeadLoopClass {
        static {
            if (true) {// 必须加if，否则编译器将提示 Initializer dose not complete normally
                System.out.println(Thread.currentThread() + " init");
                while (true) {
                }
            }
        }
    }
}

