package com.books.gaobingfa.d3;

import java.util.concurrent.*;

/**
 * 循环屏障
 *
 * 最后一个满足 await 的线程，会先执行完 CyclicBarrier 构造函数的 Runnable ，
 * 在Runnable执行完后，满足条件的所有 await 线程才会执行 await后面的代码
 *
 * Created by Administrator on 2017/8/31 0031.
 */
public class CyclicBarrierTest1 {

    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                // 在此测试中，第5个执行到 await 的线程，会继续执行该 run 方法，
                // 在该方法执行完成后，全部5个 await 的线程，才会继续执行 await 后面的代码
                // 但要注意的是，执行该 run 方法的线程，就是第5个 await 的线程，不会创建新的线程
                System.out.println(Thread.currentThread() + " >>>  run ... " );
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread() + " >>>  run ... " );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(6);


        for (int i = 0; i < 6; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread() + " await");
                    try {
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread() + " --- go on");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}
