package com.springapp.beans;

import com.springapp.beans.service.IUserListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author zhangbo
 * @date 2019-11-08
 */
public class Application {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);

        Map<String, IUserListener> beans = applicationContext.getBeansOfType(IUserListener.class);

        System.out.println(beans);

    }

}
