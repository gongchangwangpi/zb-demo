package com.zb.fund.activemq;

import com.zb.fund.utils.MessageQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
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
    
    @Scheduled(initialDelay = 2000, fixedRate = 1000 * 10)
    public void produce() throws JMSException {
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText(String.valueOf(count.getAndIncrement()));
        jmsTemplate.convertAndSend(MessageQueue.TEST_LIS, textMessage);
    }
    
}
