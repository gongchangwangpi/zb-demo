package com.middlesoftware.redis.redission;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * 基于Redission的分布式锁
 * 
 * @author zhangbo
 */
public class RedissionTest {

    public static void main(String[] args) {

        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://172.18.8.22:6379");
        singleServerConfig.setPassword("jintoufs");
        
        RedissonClient redissonClient = Redisson.create(config);

        RLock lock = redissonClient.getLock("dist_lock");
        
        lock.lock();
        
        try {
            System.out.println("dist_lock");
        } finally {
            lock.unlock();
        }

        redissonClient.shutdown();
        
    }
    
}
