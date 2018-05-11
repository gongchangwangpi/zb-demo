package com.books.gaobingfa.d2;

/**
 * wait / notify
 *
 * Created by books on 2017/4/17.
 */
public class ObjWaitNotifyTest {

    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(new Run(1));
        Thread t2 = new Thread(new Run(2));
        Thread t3 = new Thread(new Run(3));
        Thread t4 = new Thread(new Run(4));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }

    static class Run implements Runnable {
        int i ;

        public Run(int i) {
            this.i = i;
        }

        @Override
        public void run() {

            if (i <3) {
                synchronized (o1) {
                    try {
                        o1.wait();
                        System.out.println(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                synchronized (o2) {
                    try {
                        o2.notify();
                        o2.wait();
                        System.out.println(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

            System.out.println(i);
        }
    }

}
