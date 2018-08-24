package com.jdksource.util.concurrent;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 单线程环境下 LongAdder 与 AtomicLong的比较
 * 
 * @author zhangbo
 */
public class LongAdderTest {

    public static void main(String[] args) {

        LongAdder longAdder = new LongAdder();

        long s1 = System.currentTimeMillis();

        while (System.currentTimeMillis() - s1 <= 1000) {
            longAdder.increment();
        }

        System.out.println(longAdder.longValue());

        // ----- AtomicLong ----- // 
        AtomicLong atomicLong = new AtomicLong();
        long s2 = System.currentTimeMillis();

        while (System.currentTimeMillis() - s2 <= 1000) {
            atomicLong.getAndIncrement();
        }

        System.out.println(atomicLong);
    }
    
}
