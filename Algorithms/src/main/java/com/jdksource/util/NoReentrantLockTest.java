package com.jdksource.util;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class NoReentrantLockTest {

    static NoReentrantLock lock = new NoReentrantLock();

    public static void main(String[] args) {
        T t = new T(lock);
        t.start();

        T t1 = new T(lock);
        t1.start();

    }
    
    static class T extends Thread {
        NoReentrantLock lock;

        public T(NoReentrantLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            log.info("lock...");
            lock.lock();
            
            log.info("lock.... run...");
//            log.info("2 lock...");
//            lock.lock();

            SleepUtils.second(2);
            
            lock.unlock();
            log.info("unlock 1");
//            lock.unlock();
//            log.info("unlock 2");
        }
    }
    
}
