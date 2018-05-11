package com.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;

/**
 * @author zhangbo
 */
@Slf4j
public class ConnectionWatcher implements Watcher {
    
    private static final int SESSION_TIMEOUT = 5000;
    public static final String CHARSET = "UTF-8";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    
    protected ZooKeeper zk;

    public String getPath(String groupName) {
        groupName = StringUtils.trimToEmpty(groupName);
        return "/" + groupName;
    }
    
    public void connect(String host) throws IOException, InterruptedException {
        zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
//        ZooKeeper.States state = zk.getState();
        countDownLatch.await();
    }
    
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
            log.info("watch .......");
        }
    }
    
    public void close() throws InterruptedException {
        zk.close();
    }
    
}
