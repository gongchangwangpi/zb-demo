package com.zb.fund.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangbo
 */
@Slf4j
@Component
public class RabbitmqListener {
    
    @RabbitListener(queues = RabbitmqConstants.TEST_ROUTING_KEY)
    public void receive(Object message) {
        log.info("rabbitmq receive message: {}", message);
    }
    
}
