package com.test.pool;

import lombok.Getter;
import lombok.Setter;

/**
 * @author books
 */
@Getter
@Setter
public class ObjectPoolConfig {

    /**
     * 
     */
    private int corePoolSize = 10;

    /**
     *
     */
    private int maxPoolSize = 10;

    /**
     *
     */
    private long keepAliveSeconds = 60;

    /**
     *
     */
    private int queueCapacity = 20;

    /**
     * 是否在启动时初始化corePoolSize
     */
    private boolean init = true;
    
}
