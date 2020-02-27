package com.zb.fund.service.user.impl;

import com.zb.fund.domain.User;
import com.zb.fund.mapper.UserMapper;
import com.zb.fund.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangbo
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;

    @Override
    public int batchInsert(List<User> users) {
        return userMapper.batchInsert(users);
    }

    @Override
    public int batchUpdate(List<User> users) {
        return userMapper.batchUpdate(users);
    }

    @Override
    public User list() {
        return userMapper.selectByPrimaryKey(1L);
    }
}
