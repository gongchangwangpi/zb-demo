package com.guava.eventbus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class EventListener {
    
    @Subscribe
    public void handle(Event event) {
        log.info("---------- handle: {}", JSON.toJSONString(event));
    }
    
    @Subscribe
    public void handle2(Event2 event) {
        log.info("---------- handle2: {}", JSON.toJSONString(event));
    }
    
    @Subscribe
    public void handleObject(Object event) {
        log.info("---------- handleObject: {}", JSON.toJSONString(event));
    }
}
