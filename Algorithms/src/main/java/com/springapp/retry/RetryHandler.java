package com.springapp.retry;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class RetryHandler extends AbstractAutoProxyCreator {

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {
        Method[] declaredMethods = beanClass.getDeclaredMethods();

        for (Method method : declaredMethods) {
            Retry retry = method.getAnnotation(Retry.class);
            if (retry != null) {
                return new Object[]{new RetryMethodInterceptor()};
            }
        }
        
        return DO_NOT_PROXY;
    }
    
    @Slf4j
    static class RetryMethodInterceptor implements MethodInterceptor {
        
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Object result = null;
            
            log.warn("retry before ----------------------->>>>>");
            
            Method method = invocation.getMethod();
            
            Retry retry = method.getAnnotation(Retry.class);

            if (retry != null) {
                // begin retry
                int retryCount = 1;
                boolean isRetry;
                
                int count = retry.count();
                long delay = retry.delay();
                
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
                
            } else {
                
                result = invocation.proceed();
            }
            
            log.warn("retry after ----------------------->>>>>");
            return result;
        }
    }
}
