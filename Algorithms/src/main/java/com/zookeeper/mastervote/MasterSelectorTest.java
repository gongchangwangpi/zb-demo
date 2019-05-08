package com.zookeeper.mastervote;

import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class MasterSelectorTest {
    
    private static final String HOST = "172.18.8.34";

    public static void main(String[] args) throws Exception {

        List<MasterSelector> masterSelectors = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {

            ZkClient zkClient = new ZkClient(HOST);
            UserCenterService userCenterService = new UserCenterService(i, "name" + i);

            MasterSelector masterSelector = new MasterSelector(zkClient, userCenterService);
            
            masterSelectors.add(masterSelector);
            
            masterSelector.start();

        }

        TimeUnit.SECONDS.sleep(60);
        
        masterSelectors.forEach(MasterSelector::stop);
    }
    
}
