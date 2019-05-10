package com.jdksource.proxy.jdk;

import lombok.extern.slf4j.Slf4j;
import sun.misc.ProxyGenerator;

import java.lang.reflect.Proxy;

/**
 * @author zhangbo
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws Exception {
        
        // 保存生成的代理类字节码文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        byte[] $Proxy0s = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{HelloService.class});

        Class[] proxyInterface = {IHelloService.class, IHelloService2.class};

        HelloService helloService = new HelloService();

        LogInvocationHandler logInvocationHandler = new LogInvocationHandler(helloService);

        ClassLoader classLoader = Test.class.getClassLoader();

        Object proxyInstance = Proxy.newProxyInstance(classLoader, proxyInterface, logInvocationHandler);
        
        String res = ((IHelloService) proxyInstance).hello("zhangsan");
        String res2 = ((IHelloService2) proxyInstance).hello2("zhangsan");
        
        log.info("helloService: {}", helloService);
        log.info("proxyInstance: {}", proxyInstance);
        log.info("result: {}", res);
        log.info("result2: {}", res2);

    }
    
}
