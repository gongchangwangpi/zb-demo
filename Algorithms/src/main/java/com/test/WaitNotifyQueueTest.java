package com.test;

import java.util.LinkedList;
import java.util.List;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用wait和notify实现生产者和消费者
 * 
 * @author zhangbo
 */
@Slf4j
public class WaitNotifyQueueTest {
    
    private static List<Integer> queue = new LinkedList<>();
    private static final int maxSize = 10;
    
    public static void main(String[] args) {
        Producer producer = new Producer(queue, maxSize);
        Consumer consumer = new Consumer(queue, maxSize);

        producer.start();
        consumer.start();

    }
    
    static class Producer extends Thread {
        private final List<Integer> queue;
        private final int maxSize;
        private int task;

        public Producer(List<Integer> queue, int maxSize) {
            this.queue = queue;
            this.maxSize = maxSize;
            this.setName("Producer");
        }

        @Override
        public void run() {
            for(;;) {
                synchronized (queue) {
                    while (queue.size() >= maxSize) {
                        try {
                            log.info("task queue overflow, waiting for consumer");
                            queue.wait();
                        } catch (InterruptedException e) {
                            log.error("interrupted", e);
                        }
                    }
                
                    queue.add(task);
                    log.info("Producer task: {}", task);
                    task++;
                    queue.notifyAll();
                    SleepUtils.second(1);
                }
            }
        }
    }
    
    static class Consumer extends Thread {
        private final List<Integer> queue;
        private final int maxSize;

        public Consumer(List<Integer> queue, int maxSize) {
            this.queue = queue;
            this.maxSize = maxSize;
            this.setName("Consumer");
        }

        @Override
        public void run() {
            for (;;) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            log.info("task queue is empty, waiting for producer");
                            queue.wait();
                        } catch (InterruptedException e) {
                            log.error("interrupted", e);
                        }
                    }
                
                    Integer task = queue.remove(0);
                    log.info("Consumer task: {}", task);
                    queue.notifyAll();
                    SleepUtils.second(2);
                }
            }
        }
    }
}
