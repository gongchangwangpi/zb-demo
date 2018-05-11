package com.springapp.event;

import org.springframework.context.ApplicationEvent;

/**
 * 登陆事件
 * 
 * Created by books on 2017/11/3.
 */
public class LoginEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LoginEvent(Object source) {
        super(source);
    }
}
