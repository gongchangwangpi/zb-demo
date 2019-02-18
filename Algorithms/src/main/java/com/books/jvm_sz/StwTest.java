package com.books.jvm_sz;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * -Xmx512M -Xms512M -XX:+UseSerialGC -Xloggc:gc.log -XX:+PrintGCDetails  -Xmn1m -XX:PretenureSizeThreshold=50 -XX:MaxTenuringThreshold=1
 * 
 * Stop the world
 * 
 * @author zhangbo
 */

public class StwTest {

    public static void main(String[] args) {
        
        new Thread(new Print(), "Print Thread === ").start();
        new Thread(new AllocHeap(), "AllocHeap Thread >>> ").start();
        
    }
    
    static class AllocHeap implements Runnable {

        Map<Long, byte[]> map = new HashMap<>();

        @Override
        public void run() {
            try {
                while (true) {
                    // 大于450M的时候，开始清理内存
                    if (map.size() * 512 / 1024 / 1024 >= 400) {
                        System.out.println("=====准备清理=====:" + map.size());
                        map.clear();
                    }

                    for (int i = 0; i < 1024; i++) {
                        map.put(System.nanoTime(), new byte[512]);
                    }
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    
    @Slf4j
    static class Print implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (true) {
                try {
                    // 每秒打印10次
                    log.info("time: {}", System.currentTimeMillis() - start);
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
