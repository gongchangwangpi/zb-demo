package com.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author zhangbo
 */
public class UserEventFactory implements EventFactory<User> {
    
    @Override
    public User newInstance() {
        return new User();
    }
}
