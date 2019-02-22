package com.jdksource;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
@Slf4j
public class VolatileTest1 {

    static volatile int i = 0;
    
    public static void main(String[] args) throws InterruptedException {
        
        int j = i;
        log.info("before update j = {}", j);
        log.info("before update i = {}", i);

        TimeUnit.SECONDS.sleep(1);
        // update
        new Thread(new Updater()).start();
        TimeUnit.SECONDS.sleep(1);
        
//        j = i;
        log.info("after update j = {}", j);
        log.info("after update i = {}", i);
    }
    
    static class Updater implements Runnable {

        @Override
        public void run() {
            i = 5;
        }
    }
    
}
