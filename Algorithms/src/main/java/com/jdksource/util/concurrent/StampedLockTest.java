package com.jdksource.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author zhangbo
 */
@Slf4j
public class StampedLockTest {

    private static final StampedLock stampedLock = new StampedLock();
    
    private static final Map<String, String> map = new HashMap<>();
    
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        
        
        executorService.execute(() -> write());
        executorService.execute(() -> read());
        executorService.execute(() -> optimisticRead());
        
        
        executorService.shutdown();
    }
    
    private static void write() {
        long stamp = stampedLock.writeLock();
        try {
            map.put("1", "11");
            log.info("write value = 11");
            // 模拟耗时
            TimeUnit.SECONDS.sleep(2);
            
            // 调用该锁的其他方法,该锁不可重入,不要盲目在锁里面调用其他锁的方法
//            write2();
            
        } catch (InterruptedException e) {
            log.error("打断", e);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
    
    private static void write2() {
        long stamp = stampedLock.writeLock();
        try {
            map.put("2", "22");
            log.info("write value = 22");
            // 模拟耗时
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            log.error("打断", e);
        }  finally {
            stampedLock.unlockWrite(stamp);
        }
    }
    
    private static void read() {
        long stamp = stampedLock.readLock();
        try {
            String value = map.get("1");
            // 模拟耗时
            TimeUnit.SECONDS.sleep(2);
            log.info("read value = {}", value);
        } catch (InterruptedException e) {
            log.error("打断", e);
        }  finally {
            stampedLock.unlockRead(stamp);
        }
    }
    
    private static void optimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        String value = map.get("1");
        
        if (!stampedLock.validate(stamp)) {
            // 乐观读失败
            stamp = stampedLock.readLock();
            try {
                value = map.get("1");
                log.info("optimisticRead fail, readLock value = {}", value);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        } else {
            log.info("optimisticRead value = {}", value);
        }
    }
    
}
