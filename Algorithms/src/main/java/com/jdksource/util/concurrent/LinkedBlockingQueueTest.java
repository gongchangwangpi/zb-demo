package com.jdksource.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhangbo
 */
@Slf4j
public class LinkedBlockingQueueTest {

    static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    
    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                try {
                    String next = scanner.next();
                    queue.put(next);
                    log.info("put: {}", next);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        for (;;) {
            String take = queue.take();
            log.info("take: {}", take);
        }
    }
    
}
