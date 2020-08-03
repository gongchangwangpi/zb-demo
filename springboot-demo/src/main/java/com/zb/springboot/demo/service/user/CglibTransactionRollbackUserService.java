package com.zb.springboot.demo.service.user;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * cglib代理的类内部方法调用的事务回滚及传播机制测试
 *
 * 结论：cglib虽然是子类的形式代理，但通过this调用内部的方法，一样不能开启事务
 *
 * spring中的this
 *
 * @author zhangbo
 * @date 2020/8/3
 */
@Slf4j
@Service
public class CglibTransactionRollbackUserService {

    @Resource
    private UserMapper userMapper;

    public User txTest() {
        log.info("===== begin test cglib transaction =====");
        log.info("this = {}", this);
        log.info("this.getClass = {}", this.getClass());

        CglibTransactionRollbackUserService service = (CglibTransactionRollbackUserService) AopContext.currentProxy();

        User user = service.saveCglibUser();
        return user;
    }

//    @Transactional
    public User saveCglibUser() {
        User user = new User();
        user.setUsername("cglib-user");
        user.setAge(19);
        user.setCreateTime(new Date());
        user.setRegisterTime(LocalDateTime.now());
        userMapper.insert(user);

        CglibTransactionRollbackUserService service = (CglibTransactionRollbackUserService) AopContext.currentProxy();
        service.saveTestUser();

        return user;
    }

    @Transactional
    public User saveTestUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setAge(29);
        user.setCreateTime(new Date());
        user.setRegisterTime(LocalDateTime.now());
        userMapper.insert(user);

        int i = 1 / 0;

        return user;
    }

}
