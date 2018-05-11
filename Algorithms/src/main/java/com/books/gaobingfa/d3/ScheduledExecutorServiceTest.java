package com.books.gaobingfa.d3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by books on 2017/4/21.
 */
public class ScheduledExecutorServiceTest {

    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        // 以任务开始时间算周期(如果任务执行花费时间 > period周期,则下次任务会在任务执行后 立即开始)
        // 实际周期 = 任务执行时间 > period周期 ? 任务执行时间 : period周期
        scheduledExecutorService.scheduleAtFixedRate(new MyTask(), 2, 2, TimeUnit.SECONDS);

        // 以任务结束时间算周期(即 真实任务周期 = delay + 任务本身执行花费的时间)
//        scheduledExecutorService.scheduleWithFixedDelay(new MyTask(), 2, 2, TimeUnit.SECONDS);

    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
//                TimeUnit.SECONDS.sleep(5);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() / 1000 + Thread.currentThread().getName());
        }
    }

}
