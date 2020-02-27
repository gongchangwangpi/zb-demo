package com.zb.shardingjdbc.service.impl;

import com.zb.shardingjdbc.domain.User;
import com.zb.shardingjdbc.mapper.UserMapper;
import com.zb.shardingjdbc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangbo
 * @since 2020-02-08
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User get(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void save(User user) {
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
    }
}
