package com.books.gaobingfa.d4;

import java.util.concurrent.TimeUnit;

/**
 * jps 查看当前java进程id
 * jstack id 查看线程堆栈
 *
 *   Found one Java-level deadlock:
 *    =============================
 *   "线程B":
 *   waiting to lock monitor 0x000000000c74c758 (object 0x00000007d5e4d888, a java.
 *   lang.Object),
 *   which is held by "线程A"
 *   "线程A":
 *   waiting to lock monitor 0x000000000aa287c8 (object 0x00000007d5e4d898, a java.
 *   lang.Object),
 *   which is held by "线程B"
 *   Java stack information for the threads listed above:
 *   ===================================================
 *
 *   "线程B":
 *   at com.books.gaobingfa.d4.DeadLockTest.run(DeadLockTest.java:55)
 *   - waiting to lock <0x00000007d5e4d888> (a java.lang.Object)
 *   - locked <0x00000007d5e4d898> (a java.lang.Object)
 *   "线程A":
 *   at com.books.gaobingfa.d4.DeadLockTest.run(DeadLockTest.java:42)
 *   - waiting to lock <0x00000007d5e4d898> (a java.lang.Object)
 *   - locked <0x00000007d5e4d888> (a java.lang.Object)
 *
 *   Found 1 deadlock.
 *
 * Created by books on 2017/4/28.
 */
public class DeadLockTest extends Thread {

    public static void main(String[] args) throws InterruptedException {
        new DeadLockTest(fork1).start();
        new DeadLockTest(fork2).start();
        TimeUnit.SECONDS.sleep(2);
    }


    Object fork;

    static Object fork1 = new Object();
    static Object fork2 = new Object();

    public DeadLockTest(Object fork) {
        this.fork = fork;
        if (fork == fork1) {
            this.setName("线程A");
        } else {
            this.setName("线程B");
        }
    }

    @Override
    public void run() {
        if (fork == fork1) {
            synchronized (fork1) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程A拿到 fork1");
                synchronized (fork2) {
                    System.out.println("线程A拿到 fork2 --- 开始吃饭");
                }
            }

        } else {
            synchronized (fork2) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程B拿到 fork2");
                synchronized (fork1) {
                    System.out.println("线程B拿到 fork1 --- 开始吃饭");
                }
            }
        }
    }
}
