package com.middlesoftware.zookeeper;

import java.util.List;

import org.apache.zookeeper.KeeperException;

/**
 * @author zhangbo
 */
public class DeleteGroup extends ConnectionWatcher {
    
    public void delete(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        List<String> children = zk.getChildren(path, false);

        for (String child : children) {
            // -1 为强制删除
            zk.delete(child, -1);
        }
        zk.delete(path, -1);
    }

    public static void main(String[] args) throws Exception {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect("172.18.8.22");
        deleteGroup.delete("keyTest");
        deleteGroup.close();
    }
}
