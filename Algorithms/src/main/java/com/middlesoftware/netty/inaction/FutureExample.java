package com.middlesoftware.netty.inaction;

import java.util.concurrent.*;

/**
 * 
 * 
 * Created by books on 2017/11/17.
 */
public class FutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("run task1");
            }
        };

        Callable<Integer> task2 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("run task2");
                return 1;
            }
        };

        Future<?> future1 = executorService.submit(task1);
        Future<Integer> future2 = executorService.submit(task2);

        System.out.println("future1 ---- " + future1.isDone());
        System.out.println("future2 ---- " + future2.isDone());

        while (!future1.isDone()) {
        }
        System.out.println("task1 done ");
        
        while (!future2.isDone()) {
        }
        System.out.println("task2 done " + future2.get());
        
        executorService.shutdown();
    }
    
}
