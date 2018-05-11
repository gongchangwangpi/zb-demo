package com.books.bingfayishu.d5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义Lock组件，同一时刻最多允许两个线程同时获取锁
 * 
 * 暂时只实现了 {@link #lock()} 和 {@link #unlock()}方法
 * 
 * @author zhangbo
 */
public class TwinsLock implements Lock {

    /**
     * 自定义同步组件
     */
    private static class Sync extends AbstractQueuedSynchronizer {
        Sync(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("count must > 0");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int count) {
            for (;;) {
                int current = getState();
                int newCount = current - count;
                // 此处书上写的 newCount < 0 || compareAndSetState(current, newCount)
                // 应改成如下的方式才是正确
                if (newCount >= 0 && compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int count) {
            for (;;) {
                int current = getState();
                int newCount = current + count;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }
    
    private Sync sync = new Sync(2);
    
    @Override
    public void lock() {
        sync.tryAcquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.tryReleaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
