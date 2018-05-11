package com.jdksource.util.event;

/**
 * Created by books on 2017/12/27.
 */
public class Test {

    public static void main(String[] args) {
        
        EventManager eventManager = new EventManager();
        
        eventManager.addListener(new LoginListener());
        
        eventManager.publish(new LoginEvent("user login"));
        
    }
    
}
