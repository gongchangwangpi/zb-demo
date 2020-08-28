package com.middlesoftware.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

import java.util.List;

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


    /**
     * /dubbo
     *      /服务名
     *          /configurations/无子节点
     *          /routers/无子节点
     *          /providers/各个服务集群节点
     *          /consumers/各个服务集群节点
     *          
     * 
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ListGroup listGroup = new ListGroup();
//        listGroup.connect(args[0]);
//        listGroup.list(args[1]);
        listGroup.connect("172.18.8.34");
        listGroup.list("dubbo/com.jhjhome.biz.brokerage.api.EnterpriseAccountFacade/consumers");
        listGroup.close();
    }
}
