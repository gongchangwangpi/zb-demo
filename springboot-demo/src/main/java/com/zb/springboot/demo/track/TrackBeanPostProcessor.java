package com.zb.springboot.demo.track;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Slf4j
//@Component
public class TrackBeanPostProcessor extends AbstractAutoProxyCreator {

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {

        Method[] declaredMethods = beanClass.getDeclaredMethods();

        for (Method method : declaredMethods) {
            Track track = method.getDeclaredAnnotation(Track.class);
            if (track != null) {
                log.info("track init the bean: {}", beanName);
                return new Object[]{new TrackHandler()};
            }
        }

        return DO_NOT_PROXY;
    }
}
