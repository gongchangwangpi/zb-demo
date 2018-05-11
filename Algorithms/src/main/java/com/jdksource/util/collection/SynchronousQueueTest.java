package com.jdksource.util.collection;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws Exception {

        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>(true);

        boolean offer = synchronousQueue.offer("1", 1000, TimeUnit.SECONDS);
        boolean offer1 = synchronousQueue.offer("2");
        boolean offer2 = synchronousQueue.offer("3");

        System.out.println(synchronousQueue.size());

    }
    
}
