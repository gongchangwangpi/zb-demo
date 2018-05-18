package com.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * @author zhangbo
 */
public class Main {

    public static void main(String[] args) {
        
        // 消费者线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 等待策略
        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        // 
        int ringBufferSize = 1 << 8;
        
        // 创建disruptor
        Disruptor<User> disruptor = new Disruptor<>(new UserEventFactory(), ringBufferSize, executorService, ProducerType.SINGLE, waitStrategy);
        // 设置消费者
        UserEventHandler eventHandler = new UserEventHandler();
        disruptor.handleEventsWith(eventHandler);
        
        // 启动disruptor
        disruptor.start();
        
        // 获取RingBuffer
        RingBuffer<User> ringBuffer = disruptor.getRingBuffer();

        for (int i = 0; i < ringBufferSize << 2; i++) {
            // 获取下一个事件序号
            long sequence = ringBuffer.next();

            // 获取序号对应的事件对象
            User user = ringBuffer.get(sequence);

            user.setId(1L);
            user.setName("张三");
            user.setAge(i);
            // 发布事件
            ringBuffer.publish(sequence);
        }

    }
    
}
