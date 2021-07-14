package com.ext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bo6.zhang
 * @date 2021/4/20
 */
@Slf4j
@Configuration
@Component
public class ExtPtProcessor implements BeanPostProcessor, ApplicationContextAware {

    private Set<Class<?>> extClzSet = new HashSet<>(64);

    private GenericApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ExtensionPointI.class.isAssignableFrom(bean.getClass())) {

        }
        return bean;
    }

    private Object refer(Extension extension, Class<?> type) throws Exception {
        return new ExtensionPointFactoryBean<>(type).getObject();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = (GenericApplicationContext) applicationContext;
    }

    public static void main(String[] args) {
        System.out.println(Object.class.isAssignableFrom(String.class));
    }
}
