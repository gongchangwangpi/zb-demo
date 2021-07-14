package com.springapp.beans.register;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author bo6.zhang
 * @date 2021/4/21
 */
public class BeanRegisterDemo {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        registerPersonService(beanFactory);
        registerPersonService2(beanFactory);

        PersonService personService = beanFactory.getBean(PersonService.class);

        personService.hello();

    }

    private static void registerPersonService(DefaultListableBeanFactory beanFactory) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(PersonService.class);
//        beanDefinition.setPrimary(true);

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("name", "lisi");

        beanDefinition.setPropertyValues(propertyValues);

        beanFactory.registerBeanDefinition("personService", beanDefinition);
    }

    private static void registerPersonService2(DefaultListableBeanFactory beanFactory) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(PersonService2.class);
        beanDefinition.setPrimary(true);
        beanDefinition.setFactoryBeanName("");

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("name", "lisi 2");

        beanDefinition.setPropertyValues(propertyValues);

        beanFactory.registerBeanDefinition("personService2", beanDefinition);
    }

}
