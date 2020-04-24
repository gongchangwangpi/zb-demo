package com.test.thread;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 3个线程，按顺序执行
 *
 * @author zhangbo
 */
@Slf4j
public class ThreadRunInOrder2 {
    

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch owner, first;
        first = owner = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            CountDownLatch next = new CountDownLatch(1);
            Thread job = new RunJob(owner, next);
            job.start();
            owner = next;
        }
        
        log.info("main sleep 3s");
        TimeUnit.SECONDS.sleep(3);

        log.info("main countDown");
        first.countDown();
    }

    @AllArgsConstructor
    static class RunJob extends Thread {
        CountDownLatch owner;
        CountDownLatch next;

        @Override
        public void run() {
            try {
                owner.await();
                log.info(" === run");
                TimeUnit.SECONDS.sleep(1);
                next.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
