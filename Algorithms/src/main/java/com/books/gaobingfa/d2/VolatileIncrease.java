package com.books.gaobingfa.d2;

/**
 *
 * i++测试
 *
 * Created by books on 2017/4/20.
 */
public class VolatileIncrease implements Runnable {

    static volatile int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            i++;
            /*synchronized (VolatileIncrease.class) {
                i++;
            }*/
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileIncrease volatileIncrease = new VolatileIncrease();

        Thread thread1 = new Thread(volatileIncrease);
        Thread thread2 = new Thread(volatileIncrease);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);
    }
}
