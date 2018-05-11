package com.util.pool2;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * FTPClient 对象池
 * 
 * @author books
 */
public class FTPClientPool extends GenericObjectPool<FTPClient> {
    
    public FTPClientPool(FTPClientFactory factory, FTPClientPoolConfig config) {
        super(factory, config);
    }
    
    
}
