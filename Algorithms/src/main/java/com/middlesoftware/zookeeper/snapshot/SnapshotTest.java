package com.middlesoftware.zookeeper.snapshot;

import org.apache.zookeeper.server.SnapshotFormatter;

/**
 * 快速解析Zookeeper的内存快照文件
 * 
 * @author zhangbo
 */
public class SnapshotTest {

    public static void main(String[] args) throws Exception {

        String[] arr = new String[]{"E://snapshot.1e0a93"};
        
        SnapshotFormatter.main(arr);
        
    }
    
}
