package com.middlesoftware.zookeeper.mastervote;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Master选举
 * 
 * @author zhangbo
 */
@Slf4j
public class MasterSelector {
    
    private static final String MASTER_PATH = "/master_user_center";
    
    private ZkClient zkClient;
    private UserCenterService service;

    private IZkDataListener dataListener;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private volatile boolean running = false;
    private volatile boolean master = false;
    
    public MasterSelector(ZkClient zkClient, UserCenterService service) {
        this.zkClient = zkClient;
        this.service = service;
        this.dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // 节点删除时，去注册Master节点
                voteMaster();
            }
        };
    }

    private void voteMaster() {
        try {
            zkClient.createEphemeral(MASTER_PATH, service.getName());
            // 创建临时节点成功，成为master
            master = true;
            log.info("{} 成为Master", service.getName());
        } catch (RuntimeException e) {
//            log.info("{} 成为Master", service.getName());
            master = false;
            zkClient.subscribeDataChanges(MASTER_PATH, dataListener);
        }
    }
    
    public boolean isMaster() {
        return master;
    }

    public void start() {
        if (!running) {
            running = true;
            log.info("{} 启动，开始选举", service.getName());
            // 启动时开始选举
            voteMaster();
            // 
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                // 模拟释放master
                releaseMaster();
            }, 2, 5, TimeUnit.SECONDS);
        }
    }

    private void releaseMaster() {
        if (master) {
            master = false;
            zkClient.delete(MASTER_PATH);
            log.info("{} 释放master", service.getName());
        }
    }

    public void stop() {
        if (running) {
            running = false;
            zkClient.close();
            scheduledExecutorService.shutdown();
            log.info("{} 停止服务", service.getName());
        }
    }
}
