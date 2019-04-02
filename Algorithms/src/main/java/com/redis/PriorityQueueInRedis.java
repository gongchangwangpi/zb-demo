package com.redis;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的有序集合实现有序队列
 * 
 * @author zhangbo
 */
public class PriorityQueueInRedis {
    
    private JedisPool jedisPool;
    private long start;
    
    public PriorityQueueInRedis(String host) {
        jedisPool = new JedisPool(host);
        start = System.currentTimeMillis();
    }
    
    public void add(String key, Object data, long score) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.zadd(key, score + start, JSON.toJSONString(data));
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
    }
    
    public <T> Set<T> get(String key, Class<T> type) {
        Jedis jedis = jedisPool.getResource();
        long end = System.currentTimeMillis();
        try {
            // 获取数据
            Set<String> set = jedis.zrevrangeByScore(key, end, start);
            if (set == null || set.isEmpty()) {
                return new HashSet<>();
            }
            // 删除
            jedis.zremrangeByScore(key, start, end);
            Set<T> result = new HashSet<T>();
            for (String json : set) {
                result.add(JSON.parseObject(json, type));
            }
            return result;
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        PriorityQueueInRedis priorityQueueInRedis = new PriorityQueueInRedis("172.18.8.143");

        priorityQueueInRedis.add("test", new User(1L, "zhangsan"), 1000 * 5);
        priorityQueueInRedis.add("test", new User(2L, "lisi"), 1000 * 10);
        priorityQueueInRedis.add("test", new User(3L, "wangwu"), 1000 * 6);
        // 有序集合，如果值相同，则会覆盖score
        priorityQueueInRedis.add("test", new User(1L, "zhangsan"), 1000 * 7);
        
        while (true) {
            Set<User> set = priorityQueueInRedis.get("test", User.class);
            System.out.println("===== >>>>> " + JSON.toJSONString(set));
            TimeUnit.SECONDS.sleep(1);
        }

    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class User {
        private Long id;
        private String name;
    }
}
