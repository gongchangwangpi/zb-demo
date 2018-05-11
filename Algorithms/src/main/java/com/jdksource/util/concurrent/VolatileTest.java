package com.jdksource.util.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Volatile只保证可见性，不能控制并发
 * 此例多运行几次，可能会出现 map 的 size 大于100的情况
 *
 * Created by Administrator on 2017/8/30 0030.
 */
public class VolatileTest {

    private static volatile int count = 0;

    private static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    add(i + "", i + "");
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    add(i + 100 + "", i + 100 + "");
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    add(i + 300 + "", i + 300 + "");
                }
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    add(i + 400 + "", i + 400 + "");
                }
            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    add(i + 500 + "", i + 500 + "");
                }
            }
        });
        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    add(i + 600 + "", i + 600 + "");
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();

//        TimeUnit.SECONDS.sleep(2);
        System.out.println(map.size());
    }

    public static void add(String key, String value) {
        if (count < 100) {
//            System.out.println(count);
//            System.out.println(key);
            map.put(key, value);
            // volatile 只保证可见性，在参与计算的情况，可能计算后的值还没有来得及写入主存
            // 所以在另外一个线程中读取 count 的时候，还是没有计算之前的值
            count++;
        }
    }

}
