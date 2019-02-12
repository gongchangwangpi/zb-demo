package com.jdksource.util.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 *
 * Created by books on 2017/4/10.
 */
public class ReentrantLockTest {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        for (long i = 0; i < 2_300_000_000L; i++) {

            lock.lock();
            try {

            } finally {
                lock.unlock();
            }

        }

    }

}
