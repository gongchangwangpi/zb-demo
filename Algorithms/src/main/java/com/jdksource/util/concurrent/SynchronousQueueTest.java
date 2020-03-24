package com.jdksource.util.concurrent;

import java.util.concurrent.SynchronousQueue;

/**
 * @author zhangbo
 * @date 2020/3/17
 **/
public class SynchronousQueueTest {

    public static void main(String[] args) throws Exception {

        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        String s = synchronousQueue.poll();
        System.out.println(s);

        // 阻塞
        String take = synchronousQueue.take();
        System.out.println(true);
    }

}
