package com.middlesoftware.zookeeper.keyvalue;

import java.io.UnsupportedEncodingException;

import com.middlesoftware.zookeeper.ConnectionWatcher;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

/**
 * 使用zookeeper实现一个key/value的储存器
 * key为path,value为data
 * 
 * @author zhangbo
 */
public class ActiveKeyValueStore extends ConnectionWatcher {
    
    public void put(String key, String value) throws KeeperException, InterruptedException {
        String path = getPath(key);
        Stat exists = zk.exists(path, false);
        if (exists == null) {
            // 不存在key
            zk.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zk.setData(path, value.getBytes(), -1);
        }
    }
    
    public String get(String key, Watcher watcher) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        String path = getPath(key);
        byte[] data = zk.getData(path, watcher, null);
        return new String(data, CHARSET);
    }
}
