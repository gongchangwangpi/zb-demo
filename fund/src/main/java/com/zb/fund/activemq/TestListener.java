package com.zb.fund.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author zhangbo
 */
@Slf4j
@Component
public class TestListener {
    
    
//    @JmsListener(destination = MessageQueue.TEST_LIS)
    public void handle(TextMessage message) throws JMSException {
        String text = message.getText();
        long time = System.currentTimeMillis();
        log.info("jms handle msg start: {}, time = {}", text, time);
        if (time % 2 == 0) {
            message.acknowledge();
        }
    }
    
//    @JmsListener(destination = MessageQueue.TEST_LIS)
//    public void handle(String message) throws JMSException {
//        long time = System.currentTimeMillis();
//        log.info("jms handle msg start: {}, time = {}", message, time);
//    }
    
}
