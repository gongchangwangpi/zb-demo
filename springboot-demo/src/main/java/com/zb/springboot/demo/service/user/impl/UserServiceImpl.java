package com.zb.springboot.demo.service.user.impl;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.mapper.UserMapper;
import com.zb.springboot.demo.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangbo
 * @date 2020/4/23
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Long insert(User user) {
        userMapper.insert(user);
        return user.getId();
    }
}
