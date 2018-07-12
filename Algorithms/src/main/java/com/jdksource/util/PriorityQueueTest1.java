package com.jdksource.util;

import java.util.PriorityQueue;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * 优先队列
 * 
 * @author zhangbo
 */
@Slf4j
public class PriorityQueueTest1 {

    public static void main(String[] args) {

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        for (int i = 0; i < 50; i++) {
            // 插入时只保证部分有序，在取出时会重新排序
            priorityQueue.offer(new Random().nextInt(200));
        }
        
        while (!priorityQueue.isEmpty()) {
            int i = priorityQueue.poll();
            log.info("{}", i);
        }
        
    }
    
}
