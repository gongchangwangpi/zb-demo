package com.jdksource.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSample {

    // 代表一个ForkJoinTask
    public static class CountTask extends RecursiveTask<Long> {

        private static final long THRESHOLD = 20000L;

        private long start;

        private long end;

        public CountTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;

            // 是否采用分而治之的标识符
            boolean computeAlone = (end - start) <= THRESHOLD;

            if (computeAlone) {
                System.out.println("独自计算");
                for (long i = 0; i <= 20000; i++) {
                    sum += i;
                }
                return sum;
            } else {
                // 分治规模，即每个子任务需要处理的数据范围
                long step = (start + end) / 4;

                List<CountTask> subCountTasks = new ArrayList<>();

                // 分治的动态起始值
                long dynamicStart = start;

                for (int i = 0; i < 4; i++) {
                    // 重置分治的动态结束值
                    long dynamicEnd = dynamicStart + step;

                    if (dynamicEnd > end) {
                        dynamicEnd = end;
                    }

                    CountTask subCountTask = new CountTask(dynamicStart, dynamicEnd);
                    subCountTasks.add(subCountTask);
                    // 开启子任务
                    subCountTask.fork();
                    // 重置分治的动态起始值
                    dynamicStart = dynamicStart + step + 1;
                }

                for (CountTask subCountTask : subCountTasks) {
                    sum += subCountTask.join();
                }

                return sum;
            }
        }
    }

    public static void main(String[] args) {
        // 创建特殊的线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool(1);
        CountTask countTask = new CountTask(0, 20000L);

        long start = System.currentTimeMillis();

        // 提交任务
        ForkJoinTask<Long> result = forkJoinPool.submit(countTask);

        try {
            long sum = result.get();
            System.out.println("sum = " + sum + ", consumes = " + (System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
