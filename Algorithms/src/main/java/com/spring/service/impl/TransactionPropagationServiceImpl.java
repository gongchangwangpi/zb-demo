package com.spring.service.impl;

import com.spring.dao.UserMapper;
import com.spring.domain.User;
import com.spring.service.TransactionPropagationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试在spring环境中，单个service对象内部方法调用的事务传播机制
 *
 * Created by Administrator on 2018/9/3 0003.
 */
@Slf4j
@Service(value = "transactionPropagationService")
public class TransactionPropagationServiceImpl implements TransactionPropagationService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void required() {
        User user = new User("u_123");
        userMapper.insert(user);

        // 直接调用
//        this.requires_new();
        // 使用代理对象调用，需要配置expose-proxy=true
        // e.g.<aop:aspectj-autoproxy proxy-target-class="true" expose-proxy="true"/>
        TransactionPropagationService service = (TransactionPropagationService) AopContext.currentProxy();
        service.requires_new();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requires_new() {
        User user = new User("u_123");
        userMapper.insert(user);
    }
}
