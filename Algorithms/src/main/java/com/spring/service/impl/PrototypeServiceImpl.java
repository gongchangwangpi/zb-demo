package com.spring.service.impl;

import com.spring.service.PrototypeService;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by books on 2017/7/24.
 */
@Service(value = "prototypeService")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeServiceImpl implements PrototypeService {

//    @Resource
//    private SingletonService singletonService;

    @Override
    public String sayWorld(String name) {
        System.out.println("----- sayWorld -----");
        System.out.println("PrototypeService " + this);
//        System.out.println("SingletonService " + singletonService);
        System.out.println("----- sayWorld -----");

        return null;
    }
}
