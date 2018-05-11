package com.spring.service.impl;

import com.spring.service.PrototypeService;
import com.spring.service.SingletonService2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by books on 2017/7/24.
 */
@Service(value = "singletonService2")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingletonServiceImpl2 implements SingletonService2 {

    // 多例一旦注入Spring的单利中，就不会再变,但如果在几个地方注入，则每个地方注入的实例是不一样的
    @Resource
    private PrototypeService prototypeService;

    @Override
    public String sayHello2(String name) {
        System.out.println("----- sayHello2 -----");
        System.out.println("PrototypeService " + prototypeService);
        System.out.println("SingletonService2 " + this);
        System.out.println("----- sayHello2 -----");

        return null;
    }
}
