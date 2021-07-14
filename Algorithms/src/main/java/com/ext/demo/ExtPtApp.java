package com.ext.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author bo6.zhang
 * @date 2021/4/20
 */
public class ExtPtApp {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.ext");

        DemoService demoService = context.getBean(DemoService.class);

        demoService.test();

    }

}
