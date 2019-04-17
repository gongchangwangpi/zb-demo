package com.dubbo.test.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.Set;

/**
 * @author zhangbo
 */
public class DubboSpiTest {

    public static void main(String[] args) {
        // Dubbo SPI会一次性加载该类型的所有Class，然后在按需初始化对应的实例，且只初始化一次(缓存)
        ExtensionLoader<ISayHi> extensionLoader = ExtensionLoader.getExtensionLoader(ISayHi.class);

        ISayHi impl1 = extensionLoader.getExtension("impl1");
        impl1.say();
        
        Set<String> loadedExtensions = extensionLoader.getLoadedExtensions();
        loadedExtensions.forEach(ext -> extensionLoader.getExtension(ext).say());

        ExtensionLoader<ISayHi> extensionLoader2 = ExtensionLoader.getExtensionLoader(ISayHi.class);

        impl1 = extensionLoader.getExtension("impl1");
        impl1.say();
        
        loadedExtensions = extensionLoader2.getLoadedExtensions();
        loadedExtensions.forEach(ext -> extensionLoader2.getExtension(ext).say());

    }
    
}
