package com.jdksource.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author zhangbo
 */
public class Test {

    public static void main(String[] args) {

        // 保存生成的字节码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, System.getProperty("user.dir"));

        // 创建一个织入器
        Enhancer enhancer = new Enhancer();
        // 设置父类
        // 如果父类HelloServiceImpl是final修饰的，报错
        // 如果父类的方法是final修饰的，可以执行被代理方法的逻辑，但额外的代理逻辑不会执行
        enhancer.setSuperclass(HelloServiceImpl.class);
        // 设置需要织入的逻辑
        enhancer.setCallback(new LogInterceptor());
        // 使用织入器创建子类
        HelloService helloService = (HelloService) enhancer.create();

        String res = helloService.hello("test");

        System.out.println(res);

    }
    
}
