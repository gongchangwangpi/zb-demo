package com.jdksource.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author zhangbo
 */
public class Test {

    public static void main(String[] args) {

        //创建一个织入器
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(HelloServiceImpl.class);
        //设置需要织入的逻辑
        enhancer.setCallback(new LogInterceptor());
        //使用织入器创建子类
        HelloService helloService = (HelloService) enhancer.create();

        String res = helloService.hello("test");

        System.out.println(res);

    }
    
}
