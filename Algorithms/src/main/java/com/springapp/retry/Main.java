package com.springapp.retry;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangbo
 */
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        context.start();

        UserService userService = context.getBean(UserService.class);

        String world = userService.say("world");

        System.out.println("----------------- say " + world);

    }
    
}
