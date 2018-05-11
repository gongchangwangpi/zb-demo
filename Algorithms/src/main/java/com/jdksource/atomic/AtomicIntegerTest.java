package com.jdksource.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by books on 2017/3/20.
 */
public class AtomicIntegerTest {

    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(1);

        System.out.println(i.compareAndSet(2, 4));

        System.out.println(i.getAndSet(4));

        System.out.println(i);

        int i1 = i.getAndIncrement();
        System.out.println(i);
        System.out.println(i1);
    }

}
