package com.zb.springboot.demo.service.user;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.mapper.UserMapper;
import com.zb.springboot.demo.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * 通过this调用，配合改写的{@see CglibAopProxy}测试，this调用内部方法，事务是否生效
 *
 * 本例中的this为生成的代理子类，但由于子类拥有父类同样的属性和方法，导致代理子类中的userMapper不能被容器正常注入
 * 需要变通的实现注入userMapper
 *
 * 本例中会开启两个事务，且saveTestUser事务回滚，但在saveCglibUser中进行了catch，所以不会影响saveCglibUser
 * 最终saveCglibUser成功执行，saveTestUser会回滚
 *
 *
 * see Algorithms/SpringCglibTest
 *
 * @author zhangbo
 * @date 2020/8/3
 */
@Slf4j
@Service
public class CglibTransactionRollbackUserThisService {

    @Resource
    protected UserMapper userMapper;

    public CglibTransactionRollbackUserThisService() {
        log.info(">>>>> CglibTransactionRollbackUserThisService Constructor <<<<< {}", this);
    }

    public User txTest() {
        log.info("===== begin test cglib transaction =====");
        log.info("this = {}", this);
        log.info("this.getClass = {}", this.getClass());

        return saveCglibUser();
    }

    @Transactional
    public User saveCglibUser() {
        User user = new User();
        user.setUsername("cglib-user");
        user.setAge(19);
        user.setCreateTime(new Date());
        user.setRegisterTime(LocalDateTime.now());

        // 代理子类也有同样的userMapper，但spring容器没有注入
//        userMapper.insert(user);
        SpringContextHolder.getBean(UserMapper.class).insert(user);
        try {
            saveTestUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User saveTestUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setAge(29);
        user.setCreateTime(new Date());
        user.setRegisterTime(LocalDateTime.now());

//        userMapper.insert(user);
        SpringContextHolder.getBean(UserMapper.class).insert(user);

        int i = 1 / 0;

        return user;
    }

}
