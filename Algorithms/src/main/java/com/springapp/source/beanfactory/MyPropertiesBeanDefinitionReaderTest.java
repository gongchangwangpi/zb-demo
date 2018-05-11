package com.springapp.source.beanfactory;

import com.springapp.source.MyService;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author zhangbo
 */
public class MyPropertiesBeanDefinitionReaderTest {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        MyPropertiesBeanDefinitionReader myPropertiesBeanDefinitionReader = new MyPropertiesBeanDefinitionReader(beanFactory);

        myPropertiesBeanDefinitionReader.loadBeanDefinitions("classpath:myproperties-bean.properties");

        MyService myService = beanFactory.getBean(MyService.class);

        System.out.println("-------------------" + myService);
        
    }
    
}
