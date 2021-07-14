package com.middlesoftware.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    /**
     * zookeeper客户端与服务端连接的创建是异步的，一般ZooKeeper构造器执行完毕，并不代表连接已经建好，
     * 所以在构造器完毕后进行阻塞，然后在 {@link #process(WatchedEvent)} )}
     * 判断来自服务器的异步通知事件 == SyncConnected，才代表连接已建立
     * @param host
     * @throws IOException
     * @throws InterruptedException
     */
    public void connect(String host) throws IOException, InterruptedException {
        zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
//        ZooKeeper.States state = zk.getState();
        countDownLatch.await();
    }
    
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
            log.info("KeeperState.SyncConnected .......");
        }

        log.info("process event = {}", watchedEvent);

//        try {
//            log.info("----- 模拟耗时5秒");
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    
    public void close() throws InterruptedException {
        zk.close();
    }
    
}
