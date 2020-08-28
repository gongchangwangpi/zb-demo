package com.middlesoftware.zookeeper;

import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

/**
 * @author zhangbo
 */
@Slf4j
public class ExistTest implements Watcher {
    private static final String ROOT_LOCK = "/dist_lock";

    private static final String LOCK_PRE = "lock-";

    private static final int SESSION_TIMEOUT = 5000;

    private ZooKeeper zk;

    private String currentLock;

    private CountDownLatch countDownLatch;
    
    
    public static void main(String[] args) {
        ExistTest existTest = new ExistTest();
        
        existTest.init("172.18.8.22");
        
        existTest.exist();

    }

    @Override
    public void process(WatchedEvent event) {
        log.info("process: event {}", event);
    }

    // 链接zk
    public void init(String host) {
        try {
            zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
            
        } catch (Exception e) {
            log.error("init error", e);
            throw new RuntimeException("create distribution lock error");
        } 
    }
    
    public void exist() {
        try {
            // exists一个不存在的节点，返回null
            Stat exists = zk.exists("/testtttt", true);
            
            log.info("exits: {}", exists);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
