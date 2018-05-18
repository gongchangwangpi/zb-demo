package com.disruptor;

import com.alibaba.fastjson.JSON;
import com.books.bingfayishu.d4.SleepUtils;
import com.lmax.disruptor.EventHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class UserEventLogHandler implements EventHandler<User> {
    
    @Override
    public void onEvent(User event, long sequence, boolean endOfBatch) throws Exception {
        SleepUtils.millis(10);
        log.info("event: {}, sequence: {}, endOfBatch: {}, {}", JSON.toJSON(event), sequence, endOfBatch, event);
    }
}
