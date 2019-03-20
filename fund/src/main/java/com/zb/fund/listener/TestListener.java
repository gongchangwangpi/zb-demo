package com.zb.fund.listener;

import com.zb.fund.utils.MessageQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangbo
 */
@Slf4j
@Component
public class TestListener {
    
    @JmsListener(destination = MessageQueue.TEST_LIS)
    public void handle(String msg) {
        log.info("jms handle msg start: {}", msg);
    }
    
}
