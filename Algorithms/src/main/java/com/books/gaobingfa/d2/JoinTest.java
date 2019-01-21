package com.books.gaobingfa.d2;

import java.util.concurrent.TimeUnit;

/**
 * join 方法
 *  用Thread对象的Monitor,测试Thread.join()方法,在线程执行结束,
 *  是调用该线程对象的notify()来唤醒加入的线程
 *
 * Created by books on 2017/4/19.
 */
public class JoinTest {

    private static volatile int i = 0;

    private static Object obj = new Object();

    private static Thread thread = new Thread(new Run());

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Run2());
        t1.start();

        thread.start();
        thread.join();// join的线程对象，不能再作为其他地方的wait方法的
        // 否则，在线程执行结束，是通过该线程对象的notifyAll来通知被加入的线程
        // 会唤醒其他地方的等待线程
        System.out.println(i);

    }

    static class Run implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (i = 0; i < 1000000; i++) {

            }
        }
    }

    static class Run2 implements Runnable {

        @Override
        public void run() {
            try {
                synchronized (thread) {
                    // 用Thread对象的Monitor,测试Thread.join()方法,在线程执行结束,
                    // 是调用该线程对象的notify()来唤醒加入的线程
                    thread.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run2");
        }
    }
}
