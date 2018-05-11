package com.spring.service;

/**
 * Created by Administrator on 2017/7/31 0031.
 */
public interface RedisService {

    /**
     * 放入redis
     *
     * @param key
     * @param value
     * @param timeout 有限期 second
     */
    void put(String key, String value, long timeout);

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 持久化
     *
     * @param key
     * @param value
     */
    void persist(String key, String value);

    /**
     * 删除
     *
     * @param key
     */
    void delete(String key);

    /**
     * 设置对于key的存活时间
     * TTL: time to live
     *
     * @param key
     * @param timeout second
     */
    void setTTL(String key, long timeout);

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    boolean hasKey(String key);
}
