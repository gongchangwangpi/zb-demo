package com.jdksource.util;

/**
 * 线程优先级
 *
 * 一个新创建线程的优先级同创建他的线程是一样的
 * 如果创建线程是守护线程，那么被创建出来的线程也是守护线程
 *
 * Created by books on 2017/4/17.
 */
public class ThreadPriorityTest {

    public static void main(String[] args) {

        Thread main = Thread.currentThread();

        main.setPriority(3);

        Thread thread = new Thread(new T());

        System.out.println("main: " + main.getPriority());
        System.out.println("thread: " + thread.getPriority());

        thread.start();
    }


    static class T implements Runnable {

        @Override
        public void run() {
            Thread currentThread = Thread.currentThread();
            int priority = currentThread.getPriority();
            System.out.println(currentThread.getId() + ": " + priority);
            currentThread.setPriority(++priority);
            System.out.println(currentThread.getId() + ": " + priority);


            Thread thread = new Thread(new T());
            thread.start();
        }
    }
}
