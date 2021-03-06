package com.zb.springboot.demo.service;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bo6.zhang
 * @date 2021/2/7
 */
@Service
public class NotReadOnlyService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = false)
    public User notReadOnly() {
        User user = userMapper.selectById(1);
        user.setAge(0);
        userMapper.updateById(user);
        return user;
    }

}
