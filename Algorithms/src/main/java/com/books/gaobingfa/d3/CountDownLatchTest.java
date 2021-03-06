package com.books.gaobingfa.d3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch await 的线程可以是多个，当 countDown 的线程达到构造 CountDownLatch 的count时，
 * await的多个线程会同时唤醒，继续执行各自 await 后面的代码
 *
 * 执行countDown的线程在countDown之后，不会等待，会继续执行后面的代码
 *
 * CyclicBarrier 与 CountDownLatch 的还有一个区别是：
 * CyclicBarrier 可以循环使用，只要每次await的线程数量达到构造函数的要求时，便会唤醒这次await的所有线程，继续执行
 * CountDownLatch 只能使用一次，不能循环使用
 * Created by Administrator on 2017/8/30 0030.
 */
@Slf4j
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(3);
        ExecutorService e = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            // create and start threads
            e.execute(new WorkerRunnable(doneSignal, i));
        }

        log.info("main...await ");
        doneSignal.await();
        log.info("main...await run");
        
        e.shutdown();
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

                // 3个线程countDown
                if (i < 2) {
                    log.info("await " + i);
                    doneSignal.await();
                    log.info("await run" + i);
                } else if (i == 4) {
                    log.info("sleep..." + i);
                    TimeUnit.SECONDS.sleep(10);
                    log.info("countDown " + i);
                    doneSignal.countDown();
                } else {
                    log.info("countDown " + i);
                    doneSignal.countDown();
                }

            } catch (InterruptedException ex) {} // return;
        }

    }

}
