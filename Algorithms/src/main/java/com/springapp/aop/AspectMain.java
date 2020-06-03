package com.springapp.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangbo
 * @date 2020/5/15
 */
public class AspectMain {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.springapp.aop");

        PersonService personService = applicationContext.getBean(PersonService.class);
        long timestamp = personService.getTimestamp();
        System.out.println(timestamp);

        LogAspect logAspect = applicationContext.getBean(LogAspect.class);
        System.out.println(logAspect);

        applicationContext.close();
    }

}
