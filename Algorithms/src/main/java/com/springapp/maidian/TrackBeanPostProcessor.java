package com.springapp.maidian;

import com.springapp.retry.Retry;
import com.springapp.retry.RetryHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Slf4j
@Component
public class TrackBeanPostProcessor extends AbstractAutoProxyCreator {

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {

//        beanClass = beanClass.getSuperclass();

//        getBeanFactory()

        Method[] declaredMethods = beanClass.getDeclaredMethods();

        for (Method method : declaredMethods) {
            Track track = method.getDeclaredAnnotation(Track.class);
            if (track != null) {
                log.info("------------------ track init");
                return new Object[]{new TrackHandler(track.application(), track.eventType())};
            }
        }

        return DO_NOT_PROXY;
    }
}
