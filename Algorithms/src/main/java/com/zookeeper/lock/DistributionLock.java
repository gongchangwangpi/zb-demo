package com.zookeeper.lock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

/**
 * Zookeeper实现的分布式锁
 * 先创建一个持久节点 /locker
 * 然后各个客户端在分别创建临时顺序节点，创建成功后，获取持久节点 /locker下所有的子节点
 * 比较所有子节点的序号，如果自己创建的子节点序号最小，则表示获取到锁
 * 否则，将监听 exist()比自己序号小一个的子节点
 * 在watcher中收到通知时，在获取所有的子节点，比较序号，如果自己最小，则获取到锁
 * 否则重新执行监听
 * 
 * @author zhangbo
 */
@Slf4j
public class DistributionLock implements Watcher {
    
    private static final String ROOT_LOCK = "/dist_lock";
    
    private static final String LOCK_PRE = "lock-";

    private static final int SESSION_TIMEOUT = 5000;
    
    private ZooKeeper zk;
    
    private String currentLock;
    
    private CountDownLatch countDownLatch;

    public DistributionLock(String host) {
        // 链接zk
        try {
            zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
            // 判断根节点
            Stat exists = zk.exists(ROOT_LOCK, false);
            if (exists == null) {
                // 不存在，则创建持久化根节点
                zk.create(ROOT_LOCK, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            log.error("init error", e);
            throw new RuntimeException("create distribution lock error");
        }
    }

    public void lock() {
        try {
            tryLock();
        } catch (Exception e) {
            log.error("lock error", e);
            throw new RuntimeException("lock error");
        }
    }
    
    public void unlock() {
        try {
            log.info("{} 准备删除锁：{}", this, currentLock);
            // 获取节点的数据和当前线程ID比较
            byte[] data = zk.getData(currentLock, false, null);
            if (!String.valueOf(Thread.currentThread().getId()).equals(new String(data))) {
                throw new IllegalStateException();
            }
            
            zk.delete(currentLock, -1);
            currentLock = null;
            zk.close();
        } catch (Exception e) {
            log.error("unlock error", e);
            throw new RuntimeException("unlock error");
        }
    }

    private void tryLock() throws KeeperException, InterruptedException {
        String path = ROOT_LOCK + "/" + LOCK_PRE;
        // 创建临时顺序子节点,data为当前线程ID
        String data = String.valueOf(Thread.currentThread().getId());
        String currentLock = zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        this.currentLock = currentLock;
        log.info("{} 创建了锁节点: {}", this, currentLock);
        
        getLocksAndCompare(currentLock);
    }

    private void getLocksAndCompare(String currentLock) throws KeeperException, InterruptedException {
        // 获取所有的子节点
        List<String> locks = zk.getChildren(ROOT_LOCK, false);
        log.info("{} 获取到所有的子节点为：{}", this, locks);

        // 比较子节点序号
        Collections.sort(locks);
        if (currentLock.substring(11).equals(locks.get(0))) {
            // 
            log.info("{} 获取到了锁: {}", this, currentLock);
            return;
        }

        // 继续监听比自己小的子节点
        String preLock = locks.get(locks.indexOf(currentLock.substring(11)) - 1);

        Stat stat = zk.exists(ROOT_LOCK + "/" + preLock, true);

        if (stat != null) {
            log.info("{} 阻塞等待获取锁：{}, 前一个锁为: {}", this, currentLock, preLock);
            this.countDownLatch = new CountDownLatch(1);
            
            // 计数等待，若等到前一个节点消失，则precess中进行countDown，停止等待，获取锁
            this.countDownLatch.await();
            this.countDownLatch = null;
            
            // 重新获取所有的子节点比较
            log.info("{} exists的子节点有变动，重新获取子节点比较: {}", this, currentLock);
        } else {
            // 
            log.info("{} exists的子节点不存在，可能已删除或其他情况，重新获取子节点比较：{}", this, currentLock);
        }
        // 重新获取子节点并比较监听
        getLocksAndCompare(currentLock);
    }

    @Override
    public void process(WatchedEvent event) {
        if (this.countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}
