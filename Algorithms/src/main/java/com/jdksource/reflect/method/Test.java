package com.jdksource.reflect.method;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class Test {

    public static void main(String[] args) {

        Class<SubClass> subClz = SubClass.class;

        Method[] methods = subClz.getMethods();

        for (Method method : methods) {
            log.info("{}", method);
        }

        log.info("----------------------");

        Class<? super SubClass> superClz = subClz.getSuperclass();
        log.info("superClz: {}", superClz);
        
        
    }
    
}
