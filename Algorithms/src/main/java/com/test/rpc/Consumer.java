package com.test.rpc;

/**
 * @author zhangbo
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        HelloService helloService = RpcFramework.refer(HelloService.class, "127.0.0.1", 10010);

        String result = helloService.hello("张三");

        System.out.println(result);

    }
    
}
