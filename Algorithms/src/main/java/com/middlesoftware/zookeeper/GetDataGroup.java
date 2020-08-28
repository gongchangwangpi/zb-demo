package com.middlesoftware.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

/**
 * @author zhangbo
 */
@Slf4j
public class GetDataGroup extends ConnectionWatcher {
    
    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        byte[] data = zk.getData(path, false, null);
        log.info("dubbo node {} data: {}", path, new String(data));
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
        GetDataGroup listGroup = new GetDataGroup();
//        listGroup.connect(args[0]);
//        listGroup.list(args[1]);
        listGroup.connect("172.18.8.34");
        listGroup.list("dubbo/com.jhjhome.biz.brokerage.api.EnterpriseAccountFacade/providers");
        listGroup.close();
    }
}
