package com.jdksource.proxy.cglib.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * spring.core下面的 MethodInterceptor
 *
 *
 * @author zhangbo
 */
@Slf4j
public class LogTargetInterceptor implements MethodInterceptor {

    private Object target;

    public LogTargetInterceptor(Object target) {
        this.target = target;
    }

    /**
     * 使用父类的target对象代替proxy子类对象
     *
     * @param proxy
     * @param method
     * @param args
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        //执行原有逻辑，注意这里是invokeSuper,target为子类对象，spring的cglib
        // org.springframework.aop.framework.CglibAopProxy.StaticUnadvisedExposedInterceptor 中的target为父类对象
        // org.springframework.aop.framework.CglibAopProxy.StaticUnadvisedInterceptor 中的target为父类对象

        // 使用代理子类进行调用
//        Object res = methodProxy.invokeSuper(proxy, args);
        // 使用父类对象直接调用，如果使用proxy对象，则会产生递归调用:StackOverflowError
        Object res = methodProxy.invoke(target, args);

        //执行织入的日志
        System.out.println("记录日志 >>> " + method.getName());

        return res;
    }
}
