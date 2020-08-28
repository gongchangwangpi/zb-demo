package com.middlesoftware.zookeeper;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

/**
 * @author zhangbo
 */
@Slf4j
public class JoinGroup extends ConnectionWatcher {
    
    public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        String joinPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        log.info("join path: {}", joinPath);  
    }

    public static void main(String[] args) throws Exception {
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect(args[0]);
        joinGroup.join(args[1], args[2]);
        // CreateMode.EPHEMERAL该模式会在客户端失去连接后自动删除创建的节点
        SleepUtils.second(10);
        joinGroup.close();
    }
    
}
