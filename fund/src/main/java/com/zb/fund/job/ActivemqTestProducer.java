package com.zb.fund.job;

import com.zb.fund.utils.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 */
@Component
public class ActivemqTestProducer {
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    
    private AtomicInteger count = new AtomicInteger();
    
//    @Scheduled(initialDelay = 2000, fixedRate = 1000 * 10)
    public void produce() {
        jmsTemplate.convertAndSend(MessageQueue.TEST_LIS, count.getAndIncrement());
    }
    
}
