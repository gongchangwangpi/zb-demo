package com.books.bingfayishu.d4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同是等待获取锁的线程，使用synchronized同步的线程状态为 BLOCKED
 * 显示使用Lock的线程状态为 WAITING，因为Lock底层使用了LockSupport来阻塞线程
 * 
 * @author zhangbo
 */
public class ThreadState {

    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        // 使用两个Blocked线程使用synchronized同步，一个获取锁成功 TIMED_WAITING，另一个被阻塞 BLOCKED
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
        // 两个Locked线程显示使用Lock加锁，获取到锁的线程 TIMED_WAITING，没有获取到锁的线程 WAITING
        new Thread(new Locked(), "LockedThread-1").start();
        new Thread(new Locked(), "LockedThread-2").start();
        
        
        SleepUtils.second(3);

        // 获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        // 遍历线程信息，仅打印线程ID和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.
                    getThreadName() + " : " + threadInfo.getThreadState());
        }
    }

    // 该线程不断地进行睡眠
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }

    // 该线程在Waiting.class实例上等待
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    // 该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }

    static Lock lock = new ReentrantLock();
    static class Locked implements Runnable {
        @Override
        public void run() {
            // 其他没有获取到锁的线程在此等待，线程状态为 WAITING
            // 显示加锁后，永远不会释放锁
            lock.lock();
            while (true) {
                // 第一个获取到锁的线程在此等待，线程状态为 TIMED_WAITING
                SleepUtils.second(100);
            }
        }
    }
}
