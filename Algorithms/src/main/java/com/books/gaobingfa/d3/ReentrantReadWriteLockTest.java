package com.books.gaobingfa.d3;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 *
 * Created by books on 2017/4/21.
 */
public class ReentrantReadWriteLockTest {

    static Lock reentrantLock = new ReentrantLock();

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static Lock writeLock = readWriteLock.writeLock();

    static Lock readLock = readWriteLock.readLock();

    static int value;

    public static int read(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            // 模拟查询耗时
            TimeUnit.SECONDS.sleep(1);

            return value;
        } finally {
            lock.unlock();
        }
    }

    public static void update(Lock lock, int value) throws InterruptedException {
        try {
            lock.lock();
            // 模拟修改耗时
            TimeUnit.SECONDS.sleep(1);

            ReentrantReadWriteLockTest.value = value;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final ReentrantReadWriteLockTest demo = new ReentrantReadWriteLockTest();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 20; i++) {
//            Thread thread = new Thread(new ReadRun(demo, reentrantLock));
            Thread thread = new Thread(new ReadRun(demo, readLock));
            thread.start();
        }

        for (int i = 0; i < 5; i++) {
//            Thread thread = new Thread(new WriteRun(demo, reentrantLock));
            Thread thread = new Thread(new WriteRun(demo, writeLock));
            thread.start();
        }

        System.out.println("用时： " + (System.currentTimeMillis() - start));
    }

    static class ReadRun implements Runnable {
        ReentrantReadWriteLockTest test;
        Lock lock;

        public ReadRun(ReentrantReadWriteLockTest test, Lock lock) {
            this.test = test;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                int value = test.read(lock);
                System.out.println(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class WriteRun implements Runnable {
        ReentrantReadWriteLockTest test;
        Lock lock;

        public WriteRun(ReentrantReadWriteLockTest test, Lock lock) {
            this.test = test;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                int value = new Random().nextInt();
                System.out.println("update " + value);
                test.update(lock, value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
