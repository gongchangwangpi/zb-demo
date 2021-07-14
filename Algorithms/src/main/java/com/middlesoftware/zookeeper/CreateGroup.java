package com.middlesoftware.zookeeper;

import com.alibaba.fastjson.JSON;
import com.util.io.ScannerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

/**
 * @author zhangbo
 */
@Slf4j
public class CreateGroup extends ConnectionWatcher {
    
    public void create(String groupName, Object data) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        String createdPath = zk.create(path, data == null ? null : JSON.toJSONBytes(data), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        
        log.info("Created path: {}", createdPath);
    }
    
    public static void main(String[] args) throws Exception {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect("127.0.0.1:2183");

        ScannerUtil.scanHandle(4, (s) -> {
            System.out.println("read from console: " + s);
            String[] arr = s.split(" ");
            try {
                createGroup.create(arr[0], args[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//        createGroup.close();

        System.in.read();
    }
}
