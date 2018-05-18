package com.disruptor;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class UserEventSoutHandler implements EventHandler<User> {
    
    @Override
    public void onEvent(User event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(Thread.currentThread() + " -- " + JSON.toJSONString(event) + " -->>> " + event);
    }
}
