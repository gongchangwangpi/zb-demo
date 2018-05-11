package com.test.pool;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.util.FTPPoolUtil;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 简单的对象池
 * 
 * @author books
 */
public class ObjectPool {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectPool.class);

    /**
     * 配置对象
     */
    private ObjectPoolConfig config;
    /**
     * 任务队列
     */
    private LinkedBlockingQueue<FTPClient> busy;
    /**
     * 空闲队列
     */
    private LinkedBlockingQueue<FTPClient> idle;

    private final AtomicInteger size = new AtomicInteger(0);
    private final AtomicLong createdCount = new AtomicLong(0);
    private final AtomicLong releasedCount = new AtomicLong(0);
    private final AtomicLong borrowedCount = new AtomicLong(0);
    private final AtomicLong returnedCount = new AtomicLong(0);



    public ObjectPool(ObjectPoolConfig config) {
        init(config);
    }

    public FTPClient getFTPClient(long timeout) {
        // 先从空闲队列中取
        FTPClient client = idle.poll();
        // //get the current time stamp
        long now = System.currentTimeMillis();

        while (true) {
            if (client != null) {
                borrowedCount.incrementAndGet();
                busy.offer(client);
                return client;
            }

            // 空闲队列没有
            // 判断现在总的FTPClient数量是否超过maxPoolSize
            if (size.get() < this.config.getMaxPoolSize()) {
                // atomic double check
                if (size.addAndGet(1) > this.config.getMaxPoolSize()) {
                    // if we got here, two threads passed through the first if
                    size.decrementAndGet();
                } else {
                    // 初始化一个FTPClient
                    client = createFTPClient();
                    busy.offer(client);
                    return client;
                }
                
            } // end if
            
            long maxWait = timeout == -1 ? Long.MAX_VALUE : timeout;
            try {
                client = idle.poll(maxWait, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                LOGGER.error("Pool wait interrupted.");
                throw new RuntimeException("Pool wait interrupted.");
            }
            
            if (maxWait == 0 && client == null) {
                // 没有等待切还没有获取到
                throw new RuntimeException("No wait: Pool empty. Unable to fetch a FTP client, none available["+busy.size()+" in use].");
            }

            if (client == null) {
                if ((System.currentTimeMillis() - now) >= maxWait) {
                    throw new RuntimeException("[" + Thread.currentThread().getName()+"] " +
                            "Timeout: Pool empty. Unable to fetch a FTP client in " + (maxWait / 1000) +
                            " seconds, none available[size:"+size.get() +"; busy:"+busy.size()+"; idle:"+idle.size()+"; last wait:"+maxWait+"].");
                } else {
                    //no timeout, lets try again
                    continue;
                }
            }
        } // end while
    }
    
    public void returnFTPClient(FTPClient client) {
        if (client == null) {
            return ;
        }
        
        // 计数器
        returnedCount.incrementAndGet();
        // 从busy移除
        if (busy.remove(client)) {
            // 加入idle
            if (idle.size() >= this.config.getMaxPoolSize() || !idle.offer(client)) {
                LOGGER.debug("Connection [{}] will be closed and not returned to the pool, idle offer fail", client);
                release(client);
            }
            
        } else {
            LOGGER.debug("Connection [{}] will be closed and not returned to the pool, busy remove fail", client);
            release(client);
        }
        // 返回FTPClient的时候，将工作目录移动uploadUrl
        try {
            client.changeWorkingDirectory("/");
        } catch (IOException e) {
            LOGGER.debug("Connection [{}] will be closed and not returned to the pool, changeWorkingDirectory to uploadUrl fail", client);
            release(client);
        }
    }

    private void release(FTPClient client) {
        // 计数器
        size.decrementAndGet();
        releasedCount.incrementAndGet();
        // 释放FTPClient
        FTPPoolUtil.closeFTPClient(client);
    }

    private FTPClient createFTPClient() {
        FTPClient client = FTPPoolUtil.initFtpClient();
        
        size.incrementAndGet();
        // 已创建计数器增加1
        createdCount.incrementAndGet();
        
        return client;
    }
    
    
    private void init(ObjectPoolConfig config) {
        checkConfig(config);
        
        this.config = config;

        this.busy = new LinkedBlockingQueue<>();

        this.idle = new LinkedBlockingQueue<>();
        
        if (config.isInit()) {
            // 在启动时初始化
            int corePoolSize = config.getCorePoolSize();
            for (int i = 0; i < corePoolSize; i++) {
                FTPClient ftpClient = createFTPClient();
                idle.offer(ftpClient);
            }
        }
    }

    private void checkConfig(ObjectPoolConfig config) {
        if (config.getCorePoolSize() < 1) {
            LOGGER.error("corePoolSize must greater than 1, corePoolSize {}", config.getCorePoolSize());
            throw new IllegalArgumentException("corePoolSize must greater than 1");
        }
        // TODO 检查配置
        if (config.getMaxPoolSize() < 1) {
            LOGGER.error("maxPoolSize must greater than 1, corePoolSize {}", config.getMaxPoolSize());
            throw new IllegalArgumentException("maxPoolSize must greater than 1");
        }
    }
    
    
    static class PoolCleaner extends TimerTask {

        @Override
        public void run() {
            
        }
    }
}
