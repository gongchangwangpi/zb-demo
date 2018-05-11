package com.books.gaobingfa.d3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork / Join
 * <p>
 * Created by books on 2017/4/24.
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            CountTask task = new CountTask(0, 2000000000L);
            ForkJoinTask<Long> result = forkJoinPool.submit(task);
            long res = result.get();

//            long res = sum(0, 2000000000L);

            long end = System.currentTimeMillis();

            System.out.println("sum=" + res);

            System.out.println(end - start);

            System.out.println(CountTask.threadIdSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long sum(long l1, long l2) {
        long sum = 0;
        for (long i = l1; i < l2; i++) {
            sum += i;
        }
        return sum;
    }

    public static class CountTask extends RecursiveTask<Long> {

        private static final long serialVersionUID = -5202250705069850450L;
        public static Set<Long> threadIdSet = new HashSet<>();
        private static final int THRESHOLD = 10000;
        private long start;
        private long end;

        public CountTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public Long compute() {

            long sum = 0;
            boolean canCompute = (end - start) < THRESHOLD;
            if (canCompute) {
                threadIdSet.add(Thread.currentThread().getId());
                for (long i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                threadIdSet.add(Thread.currentThread().getId());
                //分成100个小任务
                long step = (start + end) / 100;
                ArrayList<CountTask>subTasks = new ArrayList<>();
                long pos = start;
                for (int i = 0; i < 100; i++) {
                    long lastOne = pos + step;
                    if (lastOne > end) lastOne = end;
                    CountTask subTask = new CountTask(pos, lastOne);
                    pos += step + 1;
                    subTasks.add(subTask);
                    subTask.fork();
                    subTask.invoke();
                }
                for (CountTask t : subTasks) {
                    sum += t.join();
                }
            }
            return sum;
        }
    }

}
