package com.mq.activemq.original.p2p;

import org.apache.activemq.broker.BrokerService;

/**
 * 自己启动一个本地的activemq服务，会在当前用户目录生成
 * activemq-data/localhost/KahaDB的储存文件
 * 
 * 
 * @author zhangbo
 */
public class BrokerServiceTest {

    public static void main(String[] args) throws Exception {

        BrokerService brokerService = new BrokerService();
        
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://127.0.0.1:61616");
        
        brokerService.start();
    }
    
}
