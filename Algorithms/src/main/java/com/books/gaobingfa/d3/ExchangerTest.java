package com.books.gaobingfa.d3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Exchanger 交换机
 * 用于两个线程间的数据交换，A线程的数据给B线程，B线程的数据给A线程
 *
 * 先到达 exchange 方法会阻塞等待 另外一个线程来 交换数据
 * 可以重复使用，只要有两个线程到达 exchange，这两个线程就完成数据交换
 * 后面到达 exchange 的线程，会继续等待下一个到达 exchange 的线程来交换数据
 * 如果最终 exchange 的线程是奇数的，则最后一个线程会阻塞
 *
 * 但是如果调用的是 如下的带有超时的方法，则在到达指定时间后，
 * 线程不会阻塞，会抛出 TimeoutException，可以在捕获异常后继续自己的逻辑
 * public V exchange(V x, long timeout, TimeUnit unit)
 *
 * Created by Administrator on 2017/8/31 0031.
 */
public class ExchangerTest {

    public static void main(String[] args) {

        final Exchanger<Map<String, Integer>> exchanger = new Exchanger<>();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1  start ");

                Map<String, Integer> map1 = new HashMap<>();
                map1.put("t1", 1);

                try {

                    Map<String, Integer> exchange = exchanger.exchange(map1);
                    System.out.println("t1  exchanged map: " + exchange);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2  start ");

                Map<String, Integer> map2 = new HashMap<>();
                map2.put("t2", 2);

                try {

                    System.out.println("t2 sleep...");
                    TimeUnit.SECONDS.sleep(2);

                    Map<String, Integer> exchange = exchanger.exchange(map2);
                    System.out.println("t2  exchanged map: " + exchange);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t3  start ");

                Map<String, Integer> map2 = new HashMap<>();
                map2.put("t3", 3);

                try {
                    Map<String, Integer> exchange = exchanger.exchange(map2);
                    System.out.println("t3  exchanged map: " + exchange);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t4  start ");

                Map<String, Integer> map2 = new HashMap<>();
                map2.put("t4", 4);

                try {
                    Map<String, Integer> exchange = exchanger.exchange(map2);
                    System.out.println("t4  exchanged map: " + exchange);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();


        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t5  start ");

                Map<String, Integer> map2 = new HashMap<>();
                map2.put("t5", 4);

                try {
                    // 模拟耗时操作，使 t5 线程最后一个到达 exchange ，让 t5 自己超时
                    TimeUnit.SECONDS.sleep(5);

                    Map<String, Integer> exchange = exchanger.exchange(map2, 5, TimeUnit.SECONDS);
                    System.out.println("t5  exchanged map: " + exchange);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    System.out.println("t5 超时");
                    e.printStackTrace();
                }
            }
        });

        t5.start();
    }

}
