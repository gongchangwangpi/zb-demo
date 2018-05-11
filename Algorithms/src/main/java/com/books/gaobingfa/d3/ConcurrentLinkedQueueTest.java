package com.books.gaobingfa.d3;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by books on 2017/4/24.
 */
public class ConcurrentLinkedQueueTest {

    public static void main(String[] args) {

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        queue.offer("1");
        queue.offer("2");
        queue.offer("3");
        queue.offer("4");

        System.out.println(queue);

    }

}
