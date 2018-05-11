package com.springapp.redis_pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * Created by books on 2017/11/6.
 */
public class Receiver {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
    }
}
