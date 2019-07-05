package com.spring.service.impl;

import com.spring.dao.UserMapper;
import com.spring.domain.User;
import com.spring.domain.query.UserQuery;
import com.spring.service.TransactionPropagationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        // 使用代理对象调用，需要配置expose-proxy=true,暴露代理对象才能用，否则在获取代理对象时要报错
        // e.g.<aop:aspectj-autoproxy proxy-target-class="true" expose-proxy="true"/>
        TransactionPropagationService service = (TransactionPropagationService) AopContext.currentProxy();
//        service.requires_new();

        // 直接使用this调用，事务不生效，会随着当前事务一起提交或回滚
//        required2();
        // 使用代理对象调用，事务传播机制生效，如是REQUIRES_NEW，则required2 不会回滚
        // 如是REQUIRED，则按照spring事务传播机制，会回滚
        service.required2();

        int i = 1 / 0;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.REQUIRED)
    public void required2() {
        List<User> users = userMapper.selectListByQuery(new UserQuery());
        users.stream().findFirst().ifPresent(u -> {
            int age = u.getAge() == null ? 0 : u.getAge() + 1;
            u.setAge(age);
            userMapper.update(u);
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requires_new() {
        User user = new User("u_123");
        userMapper.insert(user);
    }
}
