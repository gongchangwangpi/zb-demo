package com.books.gaobingfa.d3;

import java.util.concurrent.Semaphore;

/**
 * 多线程访问控制信号
 *
 * 控制并发的线程数，如果 acquire 的线程数(或者 acquire 获取的信号量
 * (acquire每次获取的信号量可以不只一个))达到构造函数时，
 * 后面再次 acquire 的线程会阻塞，直到有线程 release 后才能再次 acquire
 *
 * Created by books on 2017/4/20.
 */
public class SemaphoreTest {

    private static final int MAX_AVAILABLE = 5;
    // 允许同时最多有MAX_AVAILABLE个线程访问
    private static final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

    public Object getItem() throws InterruptedException {
        available.acquire();
        System.out.println(Thread.currentThread() + " acquire");
        Thread.sleep(3000);
        return getNextAvailableItem();
    }

    public void putItem(Object x) {
        if (markAsUnused(x))
            available.release();
    }

    // Not a particularly efficient data structure; just for demo

    protected static Object[] items = new Object[]{1, 2, 3, 4, 5}; //... whatever kinds of items being managed
    protected static boolean[] used = new boolean[MAX_AVAILABLE];

    protected synchronized Object getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (!used[i]) {
                used[i] = true;
                return items[i];
            }
        }
        return null; // not reached
    }

    protected synchronized boolean markAsUnused(Object item) {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (item == items[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    static class SemaphoreRun implements Runnable {

        SemaphoreTest semaphoreTest;

        public SemaphoreRun(SemaphoreTest semaphoreTest) {
            this.semaphoreTest = semaphoreTest;
        }

        @Override
        public void run() {
            try {
                semaphoreTest.getItem();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreRun semaphoreRun1 = new SemaphoreRun(new SemaphoreTest());
        SemaphoreRun semaphoreRun2 = new SemaphoreRun(new SemaphoreTest());
        SemaphoreRun semaphoreRun3 = new SemaphoreRun(new SemaphoreTest());
        SemaphoreRun semaphoreRun4 = new SemaphoreRun(new SemaphoreTest());
        SemaphoreRun semaphoreRun5 = new SemaphoreRun(new SemaphoreTest());
        SemaphoreRun semaphoreRun6 = new SemaphoreRun(new SemaphoreTest());

        Thread thread1 = new Thread(semaphoreRun1);
        Thread thread2 = new Thread(semaphoreRun2);
        Thread thread3 = new Thread(semaphoreRun3);
        Thread thread4 = new Thread(semaphoreRun4);
        Thread thread5 = new Thread(semaphoreRun5);
        Thread thread6 = new Thread(semaphoreRun6);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

        Thread.sleep(5000);

        new SemaphoreTest().putItem(1);
    }

}
