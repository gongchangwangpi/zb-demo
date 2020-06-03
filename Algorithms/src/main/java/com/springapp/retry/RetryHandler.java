package com.springapp.retry;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
@Slf4j
public class RetryHandler extends AbstractAutoProxyCreator {

    
    
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {

//        beanClass = beanClass.getSuperclass();

        Method[] declaredMethods = beanClass.getDeclaredMethods();
        
        for (Method method : declaredMethods) {
            Retry retry = method.getDeclaredAnnotation(Retry.class);
            if (retry != null) {
                log.info("------------------ retry init");
                return new Object[]{new RetryMethodInterceptor(retry.count(), retry.delay())};
            }
        }
        
        return DO_NOT_PROXY;
    }
    
    @Slf4j
    static class RetryMethodInterceptor implements MethodInterceptor {
        private int count;
        private long delay;

        public RetryMethodInterceptor(int count, long delay) {
            this.count = count;
            this.delay = delay;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            
            Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
            
            
            Object result = null;
            
            log.warn("retry before ----------------------->>>>>");
            
            Method method = invocation.getMethod();
            Object[] arguments = invocation.getArguments();
            Object aThis = invocation.getThis();
            AccessibleObject staticPart = invocation.getStaticPart();

//            Retry retry = method.getAnnotation(Retry.class);
//
//            if (retry != null) {
                // begin retry
                int retryCount = 1;
                boolean isRetry;
                
//                int count = retry.count();
//                long delay = retry.delay();
                
                do {
                    try {
                        result = invocation.proceed();
                        isRetry = false;
                    } catch (Throwable throwable) {
                        log.warn("target method process error, prepare retry, count {}", retryCount);
                        isRetry = true;
                        TimeUnit.MILLISECONDS.sleep(delay);
                    }
                } while (isRetry && retryCount++ < count);
                
            /*} else {
                
                result = invocation.proceed();
            }*/
            
            log.warn("retry after ----------------------->>>>>");
            return result;
        }
    }
}
