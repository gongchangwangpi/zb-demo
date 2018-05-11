package com.books.gaobingfa.d3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Driver需等待其他 N 个线程countDown完毕之后才能执行
 * 在之前,会一直await阻塞，等待的线程也可以是多个，
 * 在达到目标的时候，多个等待线程都会执行await后面的代码
 *
 * Created by books on 2017/4/21.
 */
public class CountDownLatchDriver2 {

    static int N = 5;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor e = Executors.newFixedThreadPool(N);

        for (int i = 0; i < N; ++i) // create and start threads
            e.execute(new WorkerRunnable(doneSignal, i));

        doneSignal.await();           // wait for all to finish
        System.out.println("driver done");

    }

    static class WorkerRunnable implements Runnable {
        private final CountDownLatch doneSignal;
        private final int i;
        WorkerRunnable(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }
        public void run() {
            try {
                doWork(i);
                doneSignal.countDown();
            } catch (InterruptedException ex) {} // return;
        }

        void doWork(int i) throws InterruptedException {
            System.out.println(i);
            TimeUnit.SECONDS.sleep(1);
        }

    }
}


