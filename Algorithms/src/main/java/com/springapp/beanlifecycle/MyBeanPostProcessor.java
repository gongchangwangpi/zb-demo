package com.springapp.beanlifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author zhangbo
 */
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        log.info("【MyBeanPostProcessor】constructor: {}", this);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("【MyBeanPostProcessor】postProcessBeforeInitialization: {}, {}", beanName, bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("【MyBeanPostProcessor】postProcessAfterInitialization: {}, {}", beanName, bean);
        return bean;
    }
}
