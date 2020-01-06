package com.springapp.beanlifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * bean生命周期
 * 
 * @author zhangbo
 */
@Slf4j
public class TestBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    public TestBean() {
        log.info("1 --------- constructor TestBean");
    }

    public void initMethod() {
        log.info("6 --------- custom initMethod");
    }
    
    public void destroyMethod() {
        log.info("8 ---------- custom destroyMethod");
    }

    @PostConstruct
    public void init() {
        log.info(" ---------- PostConstruct  ----------");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("3 ---------- setBeanFactory BeanFactoryAware: {}", beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        log.info("2 ---------- setBeanName BeanNameAware: {}", name);
    }

    @Override
    public void destroy() throws Exception {
        log.info("7 ---------- destroy DisposableBean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("5 ---------- afterPropertiesSet InitializingBean");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("4 ---------- setApplicationContext ApplicationContextAware: {}", applicationContext);
    }
}
