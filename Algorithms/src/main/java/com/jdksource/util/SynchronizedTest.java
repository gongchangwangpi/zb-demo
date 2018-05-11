package com.jdksource.util;

/**
 * 可重入锁测试
 * 其他测试参见 MyLock, MyLockTest, ReentrantLockTest, SynchronizedTest
 *
 * Created by Administrator on 2017/6/13 0013.
 */
public class SynchronizedTest {

    public static synchronized void get1() {
        System.out.println("get1");
        get2();
    }

    public static synchronized void get2() {
        System.out.println("get2");
    }

    public static void main(String[] args) {
        get1();
    }
}
