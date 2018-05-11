package com.dubbo.consumer;

import com.alibaba.dubbo.config.*;
import com.dubbo.api.HelloService;

/**
 * @author zhangbo
 */
public class ConsumerConfiguration {
    
    public static HelloService init() {

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(28080);
        protocol.setThreads(2);

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("consumer-hello");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://172.18.8.45:2181");

        // 服务提供者暴露服务配置
        ReferenceConfig<HelloService> config = new ReferenceConfig<>();
        config.setInterface(HelloService.class);
        config.setApplication(application);
        config.setRegistry(registry);
        config.setMock("com.dubbo.consumer.mock.HelloServiceMock");
        config.setCheck(false);

        // 发现服务
        return config.get();
    }
    
}
