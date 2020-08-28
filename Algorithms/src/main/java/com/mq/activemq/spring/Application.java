package com.mq.activemq.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by Administrator on 2018/3/5 0005.
 */
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ActiveMQConfig.class);
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);


    }

}
