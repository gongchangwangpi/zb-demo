package com.zb.springboot.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhangbo
 * @date 2020/8/4
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext staticApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        staticApplicationContext = applicationContext;
    }

    public static  <T> T getBean(Class<T> type) {
        return staticApplicationContext.getBean(type);
    }

}
