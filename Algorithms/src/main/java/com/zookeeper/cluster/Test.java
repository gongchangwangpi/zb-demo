package com.zookeeper.cluster;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * @author zhangbo
 */
public class Test {
    
//    private static final String SERVER_URL = "172.18.8.143:2181,172.18.8.143:2182,172.18.8.143:2183";
    private static final String SERVER_URL = "172.18.8.143:2181";

    public static void main(String[] args) {

        ZkClient zkClient = new ZkClient(SERVER_URL);

        String path = zkClient.create("/test", "123", CreateMode.PERSISTENT);
        System.out.println(path);

//        Object data = zkClient.readData("/test");
//        System.out.println(data);

//        boolean delete = zkClient.delete("/test");
//        System.out.println(delete);
    }
    
}
