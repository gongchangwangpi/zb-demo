package com.books.bingfayishu.d6;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * Fork/Join 
 * 
 * 计算从start到end的和
 * 
 * @author zhangbo
 */
@Slf4j
public class ForkJoinCountTask extends RecursiveTask<Integer> {

    private static final long serialVersionUID = 6213319662173872141L;

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 生成一个计算任务，负责计算1+2+3+4+5+6
        ForkJoinCountTask forkJoinCountTask = new ForkJoinCountTask(1, 1000);
        // 执行一个任务
        Future<Integer> result = forkJoinPool.submit(forkJoinCountTask);
        try {
            log.info("compute result: {}", result.get());
        } catch (Exception e) {
        }

        log.info("use time: {}ms", System.currentTimeMillis() - startTime);
        
        log.info("compute count: {}", computeCount.get());
    }
    
    // 阈值，即每个子任务执行的最大的任务
    private static final int THRESHOLD = 16;
    private static AtomicInteger computeCount = new AtomicInteger(0);
    
    private int start;
    private int end;

    public ForkJoinCountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = end - start <= THRESHOLD;
        
        if (canCompute) {
            log.info("compute");
            computeCount.incrementAndGet();
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 继续分隔任务
            int middle = (start + end) / 2;
            ForkJoinCountTask leftTask = new ForkJoinCountTask(start, middle);
            ForkJoinCountTask rightTask = new ForkJoinCountTask(middle + 1, end);
            
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            
            // 等待子任务执行完，获取执行结果
            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();

            // 合并子任务的结果
            sum = leftResult + rightResult;
        }

        return sum;
    }
}
