package com.test.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by books on 2017/10/16.
 */
public class ThreadPoolCallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        
        int thread = 20;

        List<String> list = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(thread);

        for (int i = 0; i < 9303; i++) {
            list.add(String.valueOf(i));
        }

        int size = list.size();
        int c = size / thread;
        
        List<Future<Integer>> futures = new ArrayList<>(thread);
        
        for (int i = 0; i < thread; i++) {
            List<String> subList = null;
            if (i == thread - 1) {
                subList = list.subList((thread - 1) * c, size);
            } else {
                subList = list.subList(i * c, c * (i + 1));
            }
            Future<Integer> future = executorService.submit(new MyTask(subList));
            futures.add(future);
        }

        for (Future<Integer> future : futures) {
            System.out.println(future.get());
        }
        
        // 销毁
        executorService.shutdown();
    }
    
    
    static class MyTask implements Callable<Integer> {
        
        private List<String> list;

        public MyTask(List<String> list) {
            this.list = list;
        }


        @Override
        public Integer call() throws Exception {
            int fail = 0;
            for (String s : list) {
                System.out.println(Thread.currentThread().getName() + "\t" + s);
                if (Integer.parseInt(s) % 15 == 0) {
                    fail++ ;
                }
            }
            return fail;
        }
    }
}
