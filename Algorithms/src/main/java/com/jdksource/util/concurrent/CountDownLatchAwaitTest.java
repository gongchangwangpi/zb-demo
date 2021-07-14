package com.jdksource.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author bo6.zhang
 * @date 2021/3/10
 */
@Slf4j
public class CountDownLatchAwaitTest {

    public static void main(String[] args) throws InterruptedException {

        int count = 3;

        // 可以先让多个线程await，然后等待子任务count down同时唤醒await的线程
        CountDownLatch countDownLatch = new CountDownLatch(count);

//        for (int i = 0; i < count; i++) {
//            final int j = i;
//            new Thread(() -> {
//                log.info(" ==== run start await");
//                try {
//                    countDownLatch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info(" ==== run end");
//            }, "AWAIT-" + j).start();
//        }

        for (int i = 0; i < count; i++) {
            final int j = i;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(j);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(" >>>> count down");
            }, "DOWN-" + i).start();
        }

        log.info(" === main thread await");
        countDownLatch.await();
        countDownLatch.await();
        log.info(" === main thread end");

    }

}
