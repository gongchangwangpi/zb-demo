package com.jdksource.util;

/**
 * 可重入锁测试
 * 其他测试参见 MyLock, ReentrantLockTest, SynchronizedTest
 *
 * Created by Administrator on 2017/6/13 0013.
 */
public class MyLockTest {

    static MyLock lock = new MyLock();

    public static void get1() {
        lock.lock();
        try {
            System.out.println("get1");
            get2();
        } finally {
            lock.unlock();
        }
    }

    public static void get2() {
        lock.lock();
        try {
            System.out.println("get2");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        get1();
    }

}
