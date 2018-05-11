package com.springapp.redis_pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息接收器
 * 
 * Created by books on 2017/11/6.
 */
public class Receiver2 {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver2.class);


    public void receiveMessage2(String message) {
        LOGGER.info("Received2 ... <" + message + ">");
    }
}
