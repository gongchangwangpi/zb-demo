package com.middlesoftware.dubbo.test.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class DubboSpiTest {

    public static void main(String[] args) throws InterruptedException {
        // Dubbo SPI会一次性加载该类型的所有Class，然后在按需初始化对应的实例，且只初始化一次(缓存)
        ExtensionLoader<ISayHi> extensionLoader = ExtensionLoader.getExtensionLoader(ISayHi.class);

        ISayHi impl1 = extensionLoader.getExtension("impl1");
        impl1.say();
        
        Set<String> loadedExtensions = extensionLoader.getLoadedExtensions();
        loadedExtensions.forEach(ext -> extensionLoader.getExtension(ext).say());
        System.out.println("-------------------------------");

        ExtensionLoader<ISayHi> extensionLoader2 = ExtensionLoader.getExtensionLoader(ISayHi.class);

        impl1 = extensionLoader.getExtension("impl1");
        impl1.say();
        
        loadedExtensions = extensionLoader2.getLoadedExtensions();
        loadedExtensions.forEach(ext -> extensionLoader2.getExtension(ext).say());

        TimeUnit.SECONDS.sleep(10);
        System.out.println("------------sleep");
        // 休眠10秒，等待修改dubbo的spi配置文件，检测是否能重新加载修改后的spi配置文件
        // 不能实时加载修改后的spi配置文件，有缓存。JDK SPI可以实时加载
        ExtensionLoader<ISayHi> extensionLoader3 = ExtensionLoader.getExtensionLoader(ISayHi.class);

        ISayHi impl3 = extensionLoader.getExtension("impl3");
        impl3.say();

        loadedExtensions = extensionLoader3.getLoadedExtensions();
        loadedExtensions.forEach(ext -> extensionLoader2.getExtension(ext).say());

    }
    
}
