package com.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池OOM
 *
 * -Xms256m -Xmx256m -XX:+UseConcMarkSweepGC -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps
 *
 * @author zhangbo
 * @date 2020-02-15
 */
@Slf4j
public class OOMTest {
    static Object lock = new Object();
    private static class Task implements Runnable {
        Object o = new Object();
        int[] a = new int[1024 * 2];
        @Override
        public void run() {
            long l = 0;
            for (int i = 0; i < 100_000_000; i++) {
                l += i;
            }
            try {
                synchronized (lock) {
                    lock.wait(20000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(" result ====== {}", l);
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(8);

        new Thread(() -> {
            while (true) {
                executorService.submit(new Task());
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "NewTaskThread").start();

        Random random = new Random();
        new Thread(() -> {
            int i = 0;
            ArrayList<byte[]> list = new ArrayList<>();
            long last = System.currentTimeMillis();
            for (;;) {
                list.add(new byte[1024 * 2]);
                i++;
                if (i % 50 == 0) {
                    final int max = i;
                    list.removeIf(bytes -> {
                        int i1 = random.nextInt(max);
                        return i1 % 5 == 0;
                    });
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((System.currentTimeMillis() - last) >= 3 * 1000) {
                    log.info("=========$$$$$$$$$$$$ Garbage");
                    last = System.currentTimeMillis();
                }
            }
        }, "NewGarbageThread").start();

        new Thread(() -> {
            while (true) {
                log.info(" ----->>>>> {}", new Date());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "LogThread").start();

//        executorService.shutdown();

    }

}
