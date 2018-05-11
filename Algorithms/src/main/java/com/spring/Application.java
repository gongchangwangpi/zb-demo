package com.spring;

import java.io.IOException;
import java.util.List;

import com.spring.controller.UserController;
import com.spring.domain.User;
import com.spring.domain.query.UserQuery;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring application 启动类
 *
 * Created by books on 2017/9/4.
 */
public class Application {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"}, true);

//        UserService userService = context.getBean(UserService.class);
//        User user = userService.selectById(1L);
//        System.out.println(user);

        UserController userController = context.getBean(UserController.class);
        List<User> list = userController.list(new UserQuery());
        System.out.println(list);


    }

}
