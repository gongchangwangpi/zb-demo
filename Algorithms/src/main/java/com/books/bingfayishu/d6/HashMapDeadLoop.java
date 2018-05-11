package com.books.bingfayishu.d6;

import java.util.HashMap;
import java.util.UUID;

/**
 * HashMap put操作引起死循环
 * 
 * 在JDK1.8下不会再引起死循环，但依然存在put丢失数据的情况
 * 
 * @author zhangbo
 */
public class HashMapDeadLoop {

    public static void main(String[] args) throws InterruptedException {

        long s = System.currentTimeMillis();

        final HashMap<String, String> map = new HashMap<>(2);
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                new Thread(() -> {
                        String uuid = UUID.randomUUID().toString();
                        map.put(uuid, uuid);
                }, "ftf" + i).start();
            }
        }, "ftf");
        t.start();
        t.join();

        System.out.println(System.currentTimeMillis() - s);
        System.out.println(map.size());
    }
    
}
