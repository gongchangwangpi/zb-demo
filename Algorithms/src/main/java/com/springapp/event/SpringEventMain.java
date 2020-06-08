package com.springapp.event;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 事件 测试启动类
 * 
 * Created by books on 2017/9/29.
 */
public class SpringEventMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext-app.xml"}, true);

        RegisterService registerService = applicationContext.getBean(RegisterService.class);
        registerService.register("zhangsan");
        
//        Register1Service register1Service = applicationContext.getBean(Register1Service.class);
//        register1Service.register("李四");
        
//        Register2Service register2Service = applicationContext.getBean(Register2Service.class);
//        register2Service.register("王五");

    }
    
}
