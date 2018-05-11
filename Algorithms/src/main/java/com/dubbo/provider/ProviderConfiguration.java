package com.dubbo.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.dubbo.api.HelloService;
import com.dubbo.provider.impl.HelloServiceImpl;

/**
 * @author zhangbo
 */
public class ProviderConfiguration {
    
    public static void init() {

        // 服务实现
        HelloService helloService = new HelloServiceImpl();

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(28080);
        protocol.setThreads(2);

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("provider-hello");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://172.18.8.45:2181");

        // 服务提供者暴露服务配置
        ServiceConfig<HelloService> config = new ServiceConfig<>();
        config.setInterface(HelloService.class);
        config.setApplication(application);
        config.setRegistry(registry);
        config.setProtocol(protocol);
        config.setRef(helloService);
//        config.setMock("");

        // 暴露及注册服务
        config.export();
    }
    
}
