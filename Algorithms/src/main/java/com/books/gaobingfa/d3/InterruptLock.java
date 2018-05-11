package com.books.gaobingfa.d3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 中断lock, 中断死锁
 * <p>
 * Created by books on 2017/4/20.
 */
public class InterruptLock implements Runnable {

    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();

    int lockNum;

    public InterruptLock(int lockNum) {
        this.lockNum = lockNum;
    }

    @Override
    public void run() {
        try {
            if (lockNum == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(500);
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread() + "完成");
            } else {
                lock2.lockInterruptibly();
                Thread.sleep(500);
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread() + "完成");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }

            System.out.println(Thread.currentThread() + "退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        InterruptLock interruptLock1 = new InterruptLock(1);
        InterruptLock interruptLock2 = new InterruptLock(2);

        Thread thread1 = new Thread(interruptLock1);
        Thread thread2 = new Thread(interruptLock2);

        thread2.start();
        thread1.start();

        Thread.sleep(2000);

        thread2.interrupt();
    }
}
