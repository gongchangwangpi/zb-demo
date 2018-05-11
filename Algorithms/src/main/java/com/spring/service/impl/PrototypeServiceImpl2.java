package com.spring.service.impl;

import javax.annotation.Resource;

import com.spring.service.PrototypeService;
import com.spring.service.PrototypeService2;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by books on 2017/7/24.
 */
@Service(value = "prototypeService2")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeServiceImpl2 implements PrototypeService2 {

//    @Resource
//    private SingletonService singletonService;

    @Resource
    private PrototypeService prototypeService;

    @Override
    public String sayWorld2(String name) {
        System.out.println("----- sayWorld2 -----");
        System.out.println("PrototypeService2 " + this);
        System.out.println("prototypeService " + prototypeService);
//        System.out.println("SingletonService " + singletonService);
        System.out.println("----- sayWorld2 -----");

        return null;
    }
}
