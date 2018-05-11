package com.books.gaobingfa.d3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by books on 2017/4/21.
 */
public class SemaphoreTest1 implements Runnable {

    Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            semaphore.acquire();

            Thread.sleep(2000);

            System.out.println(Thread.currentThread() + " acquire");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(Thread.currentThread() + " release");
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        final SemaphoreTest1 semaphoreTest1 = new SemaphoreTest1();

        for (int i = 0; i < 10; i++) {
            executorService.submit(semaphoreTest1);
        }

    }
}
