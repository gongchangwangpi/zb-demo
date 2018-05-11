package com.books.gaobingfa.d3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 其他 N 个线程，会等待 Driver countDown(25行)之后才会执行
 * 之后Driver会await阻塞,直到其他 N 个线程countDown完毕
 *
 * Created by books on 2017/4/21.
 */
public class CountDownLatchDriver {

    static int N = 5;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; ++i){ // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        doSomethingElse();            // don't let run yet
        System.out.println("driver countDown");
        startSignal.countDown();      // let all threads proceed

        for (int i = 0; i < 2*N; ++i){ // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        doSomethingElse();
        System.out.println("driver wait");
        doneSignal.await();           // wait for all to finish
        System.out.println("driver done");
    }

    private static void doSomethingElse() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;
        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }
        public void run() {
            try {
                System.out.println("worker await " + Thread.currentThread());
                startSignal.await();
                doWork();
                System.out.println("worker doWork " + Thread.currentThread());
                doneSignal.countDown();
                System.out.println("worker countDown " + Thread.currentThread());
            } catch (InterruptedException ex) {} // return;
        }

        synchronized void doWork() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}


