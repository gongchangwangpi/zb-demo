package com.util.pool2;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * FTPClient 连接池配置
 * 
 * @author books
 */
public class FTPClientPoolConfig extends GenericObjectPoolConfig {

    public FTPClientPoolConfig() {
        setMaxWaitMillis(1000 * 10);
        // 每次检查的连接数
        setNumTestsPerEvictionRun(3);
        // 多久检查一次连接池中空闲的连接
        setTimeBetweenEvictionRunsMillis(1000 * 60);
        // 空闲连接保持不被销毁的最长时间,如小于0,则默认为Long.MAX_VALUE
        setMinEvictableIdleTimeMillis(Long.MAX_VALUE);
        setTestWhileIdle(true);
        setTestOnBorrow(true);
        setTestOnReturn(true);
        setMinIdle(3);
        setMaxIdle(10);
        setMaxTotal(10);
    }
}
