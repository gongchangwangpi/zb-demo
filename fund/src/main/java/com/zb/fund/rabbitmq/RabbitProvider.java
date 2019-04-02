package com.zb.fund.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 */
@Slf4j
//@Component
public class RabbitProvider {
    
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;
    
    private static final AtomicInteger count = new AtomicInteger();
    
    @Scheduled(initialDelay = 5000, fixedRate = 1000 * 5)
    public void send() {

        amqpAdmin.declareQueue(new Queue(RabbitmqConstants.TEST_ROUTING_KEY));

        amqpTemplate.convertAndSend(RabbitmqConstants.TEST_ROUTING_KEY, count.getAndIncrement());
        log.info("rabbitmq send: {}", count.get());
    }
    
}
