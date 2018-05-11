package com.books.bingfayishu.d5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * @author zhangbo
 */
public class ReentrantLock2Test {

    private static ReentrantLock2 fairLock = new ReentrantLock2(true);
    private static ReentrantLock2 unfairLock = new ReentrantLock2(false);

    public static void main(String[] args) {
        // 公平锁是每个线程获取锁一次，然后在轮流获取锁一次
        fair();
        // 非公平锁几乎(大部分情况)都一个线程连续获取锁两次直到该线程执行完毕后，另外的线程才会获取锁
//        unfair();
    }

    public static void fair() {
        testLock(fairLock);
    }

    public static void unfair() {
        testLock(unfairLock);
    }

    private static void testLock(ReentrantLock2 lock) {
        // 启动5个Job
        for (int i = 0; i < 6; i++) {
            new Job(lock).start();
        }
    }

    private static class Job extends Thread {
        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        public void run() {
            // 连续2次打印当前的Thread和等待队列中的Thread
            // 连续两次获取锁和释放锁
            lock.lock();
            try {
                SleepUtils.second(2);
                System.out.println("Locked by: " + Thread.currentThread() + ", Waiting by: " + lock.getQueuedThreads());
            } finally {
                lock.unlock();
            }
            
            lock.lock();
            try {
                SleepUtils.second(2);
                System.out.println("Locked by: " + Thread.currentThread() + ", Waiting by: " + lock.getQueuedThreads());
            } finally {
                lock.unlock();
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.
                    getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
        
    }

}
