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
public class TestBean2 implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private TestBean testBean;

    public void setTestBean(TestBean testBean) {
        log.info("2- -------- set property before");
        this.testBean = testBean;
        log.info("2- -------- set property after");
    }

    @PostConstruct
    public void init() {
        log.info(" ---------- PostConstruct  ----------");
    }

    public TestBean2() {
        log.info("2-1 --------- constructor TestBean");
    }

    public void initMethod() {
        log.info("2-6 --------- custom initMethod");
    }
    
    public void destroyMethod() {
        log.info("2-8 ---------- custom destroyMethod");
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("2-3 ---------- setBeanFactory BeanFactoryAware: {}", beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        log.info("2-2 ---------- setBeanName BeanNameAware: {}", name);
    }

    @Override
    public void destroy() throws Exception {
        log.info("2-7 ---------- destroy DisposableBean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("2-5 ---------- afterPropertiesSet InitializingBean");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("2-4 ---------- setApplicationContext ApplicationContextAware: {}", applicationContext);
    }
}
