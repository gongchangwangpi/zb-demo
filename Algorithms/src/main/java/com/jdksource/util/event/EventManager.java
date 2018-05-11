package com.jdksource.util.event;

import java.util.List;

import java.util.ArrayList;
import java.util.EventObject;

/**
 * Created by books on 2017/12/27.
 */
public class EventManager {
    
    private List<LoginListener> listeners;
    
    public void addListener(LoginListener eventListener) {
        if (eventListener == null) {
            throw new IllegalArgumentException("null listener");
        }
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(eventListener);
    }

    public void removeDoorListener(LoginListener listener) {
        if (listeners == null)
            return;
        listeners.remove(listener);
    }
    
    public void publish(EventObject eventObject) {
        if (listeners == null) {
            return;
        }
        for (LoginListener listener : listeners) {
            listener.onEvent(eventObject);
        }
    }
}
