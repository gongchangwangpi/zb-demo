package com.springapp.event;

import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 * 
 * Created by books on 2017/9/29.
 */
public class RegisterEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public RegisterEvent(String source) {
        super(source);
    }
}
