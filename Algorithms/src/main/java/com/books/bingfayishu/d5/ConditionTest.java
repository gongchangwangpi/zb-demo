package com.books.bingfayishu.d5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ConditionTest {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    
    public static void main(String[] args) {

        Thread awaitThread = new Thread(new AwaitWorker(lock, condition));
        awaitThread.start();

        SleepUtils.second(2);
        
        Thread signalThread = new Thread(new SignalWorker(lock, condition));
        signalThread.start();

    }
    
    static class AwaitWorker implements Runnable {

        private Lock lock;
        private Condition condition;

        public AwaitWorker(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                log.info("await");
                condition.await();
                log.info("await run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    
    static class SignalWorker implements Runnable {

        private Lock lock;
        private Condition condition;

        public SignalWorker(Lock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                condition.signal();
                log.info("signal sleep");
                SleepUtils.second(3);
            } finally {
                lock.unlock();
            }
        }
    }
    
}
