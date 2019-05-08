package com.zookeeper;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class SessionTimeoutTest {

    private static final String HOST = "172.18.8.34";
    
    public static void main(String[] args) throws InterruptedException {

        ZkClient zkClient = new ZkClient(HOST, 1000, 5000);
        zkClient.createEphemeral("/test-ephemeral", "123");

        System.out.println((String) zkClient.readData("/test-ephemeral", true));

        TimeUnit.SECONDS.sleep(10);

        zkClient.createEphemeral("/test-ephemeral1", "456");
        System.out.println((String) zkClient.readData("/test-ephemeral1", true));
    }
    
}
