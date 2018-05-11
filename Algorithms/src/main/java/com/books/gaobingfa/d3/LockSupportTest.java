package com.books.gaobingfa.d3;

import java.util.concurrent.locks.LockSupport;

/**
 * 见d2包下BadSuspend.class
 *
 * 线程park阻塞后，线程状态变为WAITING
 * 即使unpark方法在park之前执行了，线程也能正常退出，不会像Thread.suspend和resume一样挂起线程
 *
 * Created by books on 2017/4/21.
 */
public class LockSupportTest {

    public static Object lock = new Object();

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("in " + getName());
                LockSupport.park(this);
                if (Thread.interrupted()) {
                    System.out.println("interrupt " + getName());
                }
                System.out.println("unpark " + getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ChangeObjectThread t1 = new ChangeObjectThread("t1");
        ChangeObjectThread t2 = new ChangeObjectThread("t2");
        
        t1.start();
        t2.start();
        Thread.sleep(3000);
        LockSupport.unpark(t1);
        t1.interrupt();
        LockSupport.unpark(t2);

        t1.join();
        t2.join();
    }

}
