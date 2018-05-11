package com.jdksource.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ThreadLocalTest {

    static ThreadLocal<Integer> integer = ThreadLocal.withInitial(() -> 0);
    
    static ThreadLocal<String> string = ThreadLocal.withInitial(() -> "s0");
    
    public static void main(String[] args) {

        Thread t1 = new Thread(new Runner(), "t1");
        Thread t2 = new Thread(new Runner(), "t2");
        
        t1.start();
        t2.start();

    }
    
    static class Runner implements Runnable {

        @Override
        public void run() {
            Integer i = integer.get();
            String s = string.get();

            for (int j = 0; j < 3; j++) {
                integer.set(i++);
                string.set(s + j);
            }
            
            log.info("after integer: {}", integer.get());
            log.info("after string : {}", string.get());
        }
    }
    
}
