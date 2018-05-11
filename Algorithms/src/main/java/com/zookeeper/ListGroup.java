package com.zookeeper;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

/**
 * @author zhangbo
 */
@Slf4j
public class ListGroup extends ConnectionWatcher {
    
    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        List<String> children = zk.getChildren(path, false);
        if (children == null || children.isEmpty()) {
            log.info("No members in group: {}", groupName);      
            return;
        }

        for (String member : children) {
            log.info("memberName: {}", member);
        }
    }

    public static void main(String[] args) throws Exception {
        ListGroup listGroup = new ListGroup();
//        listGroup.connect(args[0]);
//        listGroup.list(args[1]);
        listGroup.connect("172.18.8.22");
        listGroup.list("dubbo/com.jhjhome.api.cms.BannerFacade/providers");
        listGroup.close();
    }
}
