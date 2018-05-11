package com.spring.service.impl;

import com.spring.dao.UserMapper;
import com.spring.domain.User;
import com.spring.service.Transaction2Service;
import com.spring.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Random;

/**
 * Created by books on 2017/8/23.
 */
@Service(value = "transactionService")
public class TransactionServiceImpl implements TransactionService {

    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private Transaction2Service transaction2Service;

    @Resource
    private DataSourceTransactionManager transactionManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        User user = userMapper.selectById(1L);
        for (int i = 0; i < 3; i++) {
//            try {
//                nested((long) (i+1));
               this.requires_new((long) (i + 1));
//               manualCommit((long) (i + 1));
            // 两个单独的service方法，requires_new 是可以新建事务并挂起当前事务的
//            transaction2Service.requires_new((long) (i + 1));

//            } catch (Exception e) {
//                logger.error("修改User失败", e);
//            }
        }
    }

    /**
     * requires_new
     * 在同一个service内部的两个方法中调用，不会创建新的事务，只是加入当前事务，也不会挂起当前事务
     *
     * @see Propagation
     *
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requires_new(Long id) {
        logger.info("---------- requires_new start ----------");

            int age = new Random().nextInt(50);

            User user = new User();
            user.setId(id);
            user.setAge(age);

            userMapper.update(user);

            if (id == 2) {
                throw new IllegalArgumentException("失败了。。。");
            }

            user.setUsername("修改" + id);
            user.setAge(null);
            userMapper.update(user);

        logger.info("---------- requires_new end ----------");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        User user = userMapper.selectById(1L);
        for (int i = 0; i < 3; i++) {
            try {
//                nested((long) (i+1));
                manualCommit((long) (i+1));
//            requires_new((long) (i + 1));
            } catch (Exception e) {
                logger.error("修改User失败", e);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void nested(Long id) {
        logger.info("---------- nested start ----------");

        int age = new Random().nextInt(50);

        User user = new User();
        user.setId(id);
        user.setAge(age);

        userMapper.update(user);

        if (id == 2) {
            throw new IllegalArgumentException("失败了。。。");
        }

        user.setUsername("修改" + id);
        user.setAge(null);
        userMapper.update(user);

        logger.info("---------- nested end ----------");
    }

    @Override
    public void manualCommit(Long id) {
        logger.info("---------- manualCommit start ----------");
        // 手动开启事物，提交
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);

        try {
            int age = new Random().nextInt(50);

            User user = new User();
            user.setId(id);
            user.setAge(age);

            userMapper.update(user);

            if (id == 2) {
                throw new IllegalArgumentException("失败了。。。");
            }

            user.setUsername("修改" + id);
            user.setAge(null);
            userMapper.update(user);

            transactionManager.commit(status);
        } catch (IllegalArgumentException e) {
            logger.error("requires_new 修改失败了");
            transactionManager.rollback(status);
        }

        logger.info("---------- manualCommit end ----------");
    }
}
