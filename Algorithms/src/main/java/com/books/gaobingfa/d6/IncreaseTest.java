package com.books.gaobingfa.d6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 自增测试：3种方式,synchronized(有锁)，AtomicLong(cas),LongAdder
 * <p>
 * Created by books on 2017/5/31.
 */
public class IncreaseTest {

    private static final int MAX_THREADS = 3;                //线程数
    private static final int TASK_COUNT = 3;                 //任务数

    private static final int TARGET_COUNT = 10000000;        //目标总数

    private AtomicLong acount = new AtomicLong(0L);           //无锁的原子操作
    private LongAdder lacount = new LongAdder();
    private long count = 0;

    static CountDownLatch cdlsync = new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdlatomic = new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdladdr = new CountDownLatch(TASK_COUNT);


    // synchronized
    private synchronized long incre() {
        return ++count;
    }

    private synchronized long getCount() {
        return count;
    }

    // AtomicLong
}
