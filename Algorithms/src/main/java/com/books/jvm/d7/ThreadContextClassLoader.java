package com.books.jvm.d7;

/**
 * 观察{@link Thread#contextClassLoader}
 * 如果应用代码中没有设置过contextClassLoader，
 * 则该线程上下文类加载器默认为 AppClassLoader
 * 
 * @author zhangbo
 */
public class ThreadContextClassLoader {

    public static void main(String[] args) {

        Thread thread = new Thread();

    }
    
}
