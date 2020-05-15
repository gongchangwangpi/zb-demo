package com.zb.springboot.demo.track.controlleradvice;

import com.zb.springboot.demo.track.TrackHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Slf4j
@Component
public class TrackBeanPostProcessor extends AbstractAutoProxyCreator {

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {

        Method[] declaredMethods = beanClass.getDeclaredMethods();

        for (Method method : declaredMethods) {
            RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
            Annotation[] annotations = method.getAnnotations();
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            boolean contains = ArrayUtils.contains(declaredAnnotations, RequestMapping.class);

            if (requestMapping != null) {
                log.info("track requestMapping init the bean: {}", beanName);
                return new Object[]{new RequestMappingHandler()};
            }
        }

        return DO_NOT_PROXY;
    }
}
