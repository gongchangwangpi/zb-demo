package com.jdksource.proxy.cglib;

import com.jdksource.proxy.cglib.spring.SpringCglibTest;
import lombok.extern.slf4j.Slf4j;

/**
 * @see CglibTest
 * @see SpringCglibTest
 *
 *
 * 自己使用cglib生成的代理类，里面使用this获取的是生成的代理子类
 * 而在spring容器中，使用this获取却是该类本身，导致cglib的事务同样不能生效
 *
 *
 * @author zhangbo
 */
@Slf4j
public /*final*/ class HelloServiceImpl implements HelloService {

    public HelloServiceImpl() {
        log.info(">>>>> HelloServiceImpl Constructor : {}", this);
    }

    /**
     * 使用{@link CglibTest}测试，此时this为代理子类对象
     * 使用{@link SpringCglibTest},此时this为父类对象本身
     * 在Spring中,不论是JDK还是cglib动态代理，通过this调用内部方法，都不能开启事务
     *
     * @param name
     * @return
     */
    @Override
    public /*final*/ String hello(String name) {
        log.info("----super this: {}", this);
        log.info("----super this.getClass: {}", this.getClass());
        log.info("----super hello: {}", name);
        this.say();
        return "super hello: " + name;
    }

    @Override
    public String say() {
        log.info("----super this: {}", this);
        log.info("----super this.getClass: {}", this.getClass());
        log.info("----super say");
        return "super say";
    }

}
