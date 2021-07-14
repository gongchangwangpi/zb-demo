package com.zb.springboot.demo.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.mapper.UserMapper;
import com.zb.springboot.demo.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 * @date 2020/4/23
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final AtomicInteger count = new AtomicInteger(0);

    @Resource
    private UserMapper userMapper;

    @Override
//    @MyAspect(value = "user-insert")
    @Transactional(propagation = Propagation.REQUIRED)
    public Long insert(User user) {

        if (isTest()) {
            return -1L;
        }

        userMapper.insert(user);
//        int i = 1 / 0;
        return user.getId();
    }

    private boolean isTest() {
        System.out.println("========== UserServiceImpl isTest ============");
        return false;
    }

    @Override
    // 默认mybatis一级缓存生效时，只执行了一次查询，故前后两次查询的值是一样的
    // 可设置mybatis.local-cache-scope: statement 或者在sql中配置flushCache=true,使一级缓存失效
    @Transactional(isolation = Isolation.READ_COMMITTED) // 设置mybatis一级缓存失效后，能生效，执行了两次查询，后一次能查询出最新的值
//    @Transactional(isolation = Isolation.REPEATABLE_READ) // 设置mybatis一级缓存失效后，执行了两次查询，前后两次查询一致
//    @Transactional // 设置mybatis一级缓存失效后，执行了两次查询，前后两次查询一致
    public User txIsolationTest() {
        // sql xml中设置flushCache=true
        User user1 = userMapper.selectByUserId(1L);
        log.info("====>> select one, user1 : {}", JSON.toJSONString(user1));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            //
            log.error("InterruptedException", e);
        }
        User user2 = userMapper.selectByUserId(1L);
        log.info("====>> select two, user2 : {}", JSON.toJSONString(user2));
        if (user1.getUsername().equals(user2.getUsername())) {
            log.info("====>> true: {}, {}", user1.getUsername(), user2.getUsername());
        } else {
            log.info("====>> false: {}, {}", user1.getUsername(), user2.getUsername());
        }
        return user1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long insertPropagation() throws Exception {
        User user = new User();
        user.setUsername("name" + count.incrementAndGet());
        user.setAge(count.get());
        user.setCreateTime(new Date());
        userMapper.insert(user);

//        int i = 1 / 0;
        if (true) {
            throw new Exception("");
        }
        //
        UserService service = (UserService) AopContext.currentProxy();
        service.insertPropagationNew();

        return user.getId();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Long insertPropagationNew() {
        User user = new User();
        user.setUsername("name" + count.incrementAndGet());
        user.setAge(count.get());
        user.setCreateTime(new Date());
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public User get(Long id) {
        return userMapper.selectByUserId(id);
    }

    @Override
    public User get(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public int batchInsert(List<User> userList) {
        return userMapper.batchInsert(userList);
    }

}
