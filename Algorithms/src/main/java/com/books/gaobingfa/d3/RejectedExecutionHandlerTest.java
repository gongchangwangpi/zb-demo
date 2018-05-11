package com.books.gaobingfa.d3;

import java.util.concurrent.*;

/**
 * 任务拒绝策略
 * 该例实际执行任务个数 = corePoolSize + busy.capacity
 *
 * Created by books on 2017/4/21.
 */
public class RejectedExecutionHandlerTest {

    public static void main(String[] args) {

        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(), new MyRejectedExecutionHandler());

        for (int i = 0; i < 100; i++) {
            executorService.submit(new MyTask());
        }

    }

    static class MyRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(r.toString() + " is rejected");
        }
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println(System.currentTimeMillis() + " : " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
