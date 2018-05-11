package com.springapp.source.beanfactory;

import javax.sql.DataSource;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author zhangbo
 */
public class XmlBeanDefinitionReaderTest {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath*:applicationContext.xml");

        DataSource dataSource = beanFactory.getBean(DataSource.class);

        System.out.println("-------------------" + dataSource);

    }
    
}
