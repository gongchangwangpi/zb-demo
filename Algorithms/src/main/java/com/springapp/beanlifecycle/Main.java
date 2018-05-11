package com.springapp.beanlifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangbo
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        TestBean bean = context.getBean(TestBean.class);
        
//        log.info("-------- {}", bean);

        context.destroy();
    }
    
}
