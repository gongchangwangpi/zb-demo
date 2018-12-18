package com.spring.service.impl;

import com.spring.dao.UserMapper;
import com.spring.domain.User;
import com.spring.service.RetryTransactionService;
import com.springapp.retry.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangbo
 */
@Service(value = "retryTransactionService")
public class RetryTransactionServiceImpl implements RetryTransactionService {
    
    @Autowired
    private UserMapper userMapper;
    
    int count = 0;
    
    @Override
    @Retry(count = 5, delay = 1000)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void retry() {
        User user = new User();
        user.setAge(30);
        user.setUsername("hello world");
        userMapper.insert(user);
        
        /*if (count++ < 2) {
            // 模拟异常,在第三次时正常
            int i = 1 / 0;
        }*/
        
        User update = new User();
        update.setId(1L);
        update.setAge(30);
        update.setUsername("hello world");
        userMapper.update(update);
    }
}
