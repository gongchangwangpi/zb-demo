package com.middlesoftware.zookeeper.cluster;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class Test {
    
//    private static final String SERVER_URL = "172.18.8.143:2181,172.18.8.143:2182,172.18.8.143:2183";
//    private static final String SERVER_URL = "172.18.8.143:2181";

//    private static final String SERVER_URL = "127.0.0.1:2181";
//    private static final String SERVER_URL = "127.0.0.1:2182";
//    private static final String SERVER_URL = "127.0.0.1:2183";
    private static final String SERVER_URL = "127.0.0.1:2185,127.0.0.1:2184";

    public static void main(String[] args) throws Exception {

        ZkClient zkClient = new ZkClient(SERVER_URL);

//        String path = zkClient.create("/test", "123", CreateMode.PERSISTENT);
//        System.out.println(path);

//        Object data = zkClient.readData("/test");
//        System.out.println(data);

//        boolean delete = zkClient.delete("/test");
//        System.out.println(delete);

//        List<String> rootChildren = zkClient.getChildren("/zookeeper");
//        System.out.println(rootChildren);

        String path = zkClient.create("/zk3", "zk1_value", CreateMode.PERSISTENT);
        System.out.println(path);

        for (;;) {
            Object data = zkClient.readData("/zk3");
            System.out.println(data);
            TimeUnit.SECONDS.sleep(2);
        }
    }
    
}
