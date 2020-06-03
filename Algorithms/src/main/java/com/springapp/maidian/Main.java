package com.springapp.maidian;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.springapp.maidian");
        context.start();

        UserController userController = context.getBean(UserController.class);

        String zhangsan = userController.register("18780297073", "zhangsan");
//        System.out.println("user register return: " + zhangsan);

        String get = userController.get();
//        System.out.println("user get return: " + get);

        new Thread(() -> {
            String error = userController.error();
            System.out.println(error);
        }).start();

        TimeUnit.SECONDS.sleep(5);
        context.close();

    }
    
}
