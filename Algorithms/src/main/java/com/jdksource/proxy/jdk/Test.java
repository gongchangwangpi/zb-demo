package com.jdksource.proxy.jdk;

import java.lang.reflect.Proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws Exception {
        
        // 保存生成的代理类字节码文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Class[] proxyInterface = {IHelloService.class, IHelloService2.class};

        LogInvocationHandler logInvocationHandler = new LogInvocationHandler(new HelloService());

        ClassLoader classLoader = Test.class.getClassLoader();

        Object proxyInstance = Proxy.newProxyInstance(classLoader, proxyInterface, logInvocationHandler);
        
        String res = ((IHelloService) proxyInstance).hello("zhangsan");
        String res2 = ((IHelloService2) proxyInstance).hello2("zhangsan");
        
        log.info("result: {}", res);
        log.info("result2: {}", res2);

    }
    
}
