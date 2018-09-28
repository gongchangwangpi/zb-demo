package com.test.rpc;

/**
 * @author zhangbo
 */
public class Provider {

    public static void main(String[] args) throws Exception {

        RpcFramework.export(new HelloServiceImpl(), 10010);
        
    }
    
}
