package com.jdksource.proxy.cglib;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author zhangbo
 */
@Slf4j
public class LogInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        //执行原有逻辑，注意这里是invokeSuper,target为子类对象，spring的cglib
        // org.springframework.aop.framework.CglibAopProxy.StaticUnadvisedExposedInterceptor中的target为父类对象
        // org.springframework.aop.framework.CglibAopProxy.StaticUnadvisedInterceptor中的target为父类对象
        Object res = methodProxy.invokeSuper(target, args);
        
        //执行织入的日志
        System.out.println("记录日志 >>> " + method.getName());

        return res;
    }
}
