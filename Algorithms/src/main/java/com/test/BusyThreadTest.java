package com.test;

import java.util.concurrent.TimeUnit;

/**
 * 先 jps -mv 找到应用的pid
 * 然后 top -Hp pid 查看应用中各个线程的CPU，内存使用情况
 * jstack 线程id 查看线程的栈帧
 * 
 * 
 * @author zhangbo
 */
public class BusyThreadTest {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    i++;
                }
            }
        });

        t.setName("busyThread");
        
        t.start();
    }
    
}
