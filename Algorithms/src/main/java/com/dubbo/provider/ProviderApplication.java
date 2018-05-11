package com.dubbo.provider;

import com.alibaba.dubbo.container.Main;

/**
 * @author zhangbo
 */
public class ProviderApplication {

    public static void main(String[] args) {

        ProviderConfiguration.init();

        Main.main(args);
    }
    
}
