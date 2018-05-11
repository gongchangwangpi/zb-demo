package com.redis;

import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * 基于Redis的分布式锁
 * 
 * 
 * @author zhangbo
 */
@Slf4j
public class RedisDistributionLock {
    
    private Jedis jedis;
    
    private static final String LOCK_KEY = "dist_lock";
    
    private static final long TIMEOUT = 5;
    
    private static ConcurrentLinkedQueue<Thread> blockThreads = new ConcurrentLinkedQueue<>();

    public RedisDistributionLock() {
        jedis = new Jedis("172.18.8.22", 6379);
        jedis.auth("jintoufs");
    }
    
    public void lock() {
        log.info("{} 准备获取锁", this);
        
        jedis.connect();

        Thread currentThread = Thread.currentThread();
        String threadId = String.valueOf(currentThread.getId());
        // set if not exist, expire time
        String res = jedis.set(LOCK_KEY, threadId, "NX", "EX", TIMEOUT);
        log.info("{} 设置锁返回值：{}", this, res);
        
        if ("OK".equals(res)) {
            log.info("{} 获取到锁", this);
            return;
        } else {
            // 阻塞
            // TODO 不能用基于本地阻塞队列来实现阻塞，应pubsub实现key过期或者删除时，通知客户端
            blockThreads.offer(currentThread);
            LockSupport.park();
            // 唤醒后继续尝试
            lock();
        }
    }
    
    public void unlock() {
        log.info("{} 准备释放锁", this);
        
        String threadId = String.valueOf(Thread.currentThread().getId());

        // 使用lua脚本，使比较和删除操作的原子性
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(LOCK_KEY), Collections.singletonList(threadId));
    
        log.info("{} 删除锁返回值：{}", this, result);

        jedis.close();
        // 唤醒阻塞的线程
        Thread thread = blockThreads.poll();
        if (thread != null) {
            LockSupport.unpark(thread);
        }
    }
}
