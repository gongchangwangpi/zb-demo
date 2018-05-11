package com.spring.service.impl;

import com.spring.service.PrototypeService;
import com.spring.service.SingletonService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by books on 2017/7/24.
 */
@Service(value = "singletonService")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingletonServiceImpl implements SingletonService {

    // 多例一旦注入Spring的单利中，就不会再变,但如果在几个地方注入，则每个地方注入的实例是不一样的
    // 此处的 prototypeService 与 SingletonService 中注入的 prototypeService 是不同的实例
    @Resource
    private PrototypeService prototypeService;

    @Override
    public String sayHello(String name) {
        System.out.println("----- sayHello -----");
        System.out.println("PrototypeService " + prototypeService);
        System.out.println("SingletonService " + this);
        System.out.println("----- sayHello -----");

        return null;
    }
}
