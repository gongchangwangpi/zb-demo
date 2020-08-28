package com.middlesoftware.dubbo.test.spi;

import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class JdkSpiTest {

    public static void main(String[] args) throws InterruptedException {
        // 类加载一次，每次执行load时，都会在调用一次构造器初始化实例
        ServiceLoader<ISayHi> serviceLoader = ServiceLoader.load(ISayHi.class);
        serviceLoader.forEach(ISayHi::say);
        
        System.out.println("---------------------------------");
        
        serviceLoader = ServiceLoader.load(ISayHi.class);
        serviceLoader.forEach(ISayHi::say);

        // 休眠10秒，等待手动修改spi配置文件，检查是否能实时加载修改后的spi配置
        // 结果：可以实时加载修改后的配置
        TimeUnit.SECONDS.sleep(10);
        System.out.println("------------sleep");

        serviceLoader = ServiceLoader.load(ISayHi.class);
        serviceLoader.forEach(ISayHi::say);
        
    }
    
}
