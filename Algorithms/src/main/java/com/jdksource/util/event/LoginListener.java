package com.jdksource.util.event;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Created by books on 2017/12/27.
 */
public class LoginListener<LoginEvent> implements EventListener {
    
    public void onEvent(EventObject eventObject) {
        System.out.println("listener ---> " + eventObject.getSource());
    }
    
}
