package com.books.gaobingfa.d2;

/**
 * wait方法会先获取同步对象object的对象锁，执行完后会释放资源
 * notify方法同样会先获取同步对象object的对象锁，
 * 但由于notify是在synchronized同步代码块中，所以会在同步代码执行完后才会释放资源
 *
 * 所以T1并不是在notify后立即执行，而是等待T2的同步代码(Thread.sleep(2000))执行完释放资源后才会继续执行
 *
 * <p>
 *     Object.wait()和Thread.sleep()方法都可以让线程等待若干时间。除了wait()可以被唤醒外，
 *     另外一个主要区别就是wait()方法会释放目标对象的锁，而Thread.sleep()方法不会释放任何资源。
 * </p>
 *
 * Created by books on 2017/4/17.
 */
public class SimpleWN {
    final static Object object = new Object();

    public static class T1 extends Thread {
        public void run()

        {
            synchronized (object) {

                System.out.println(System.currentTimeMillis() + ":T1 start! ");
                try {
                    System.out.println(System.currentTimeMillis() + ":T1 wait for object ");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T1 end!");
            }
        }
    }

    public static class T2 extends Thread {
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T2 start! notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis() + ":T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }
}
