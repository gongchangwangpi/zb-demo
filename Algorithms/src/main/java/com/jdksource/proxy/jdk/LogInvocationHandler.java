package com.jdksource.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class LogInvocationHandler implements InvocationHandler {
    
    private Object target;

    public LogInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        // 可以选择在方法执行前拦截
        
        Object res = method.invoke(target, args);
        
        // 此处选择在方法执行完后
        // 可以自由选择拦截哪些方法
        if ("hello".equals(method.getName())) {
            log.info("invoke method:{}, args:{}", method.getName(), args);
        }
        
        return res;
    }
}
