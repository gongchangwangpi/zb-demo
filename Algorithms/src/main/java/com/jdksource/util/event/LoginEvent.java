package com.jdksource.util.event;

import java.util.EventObject;

/**
 * 登陆事件
 * 
 * Created by books on 2017/12/27.
 */
public class LoginEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public LoginEvent(Object source) {
        super(source);
    }
}
