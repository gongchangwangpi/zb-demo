package com.activemq.original.p2p;

import org.apache.activemq.broker.BrokerService;

/**
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
