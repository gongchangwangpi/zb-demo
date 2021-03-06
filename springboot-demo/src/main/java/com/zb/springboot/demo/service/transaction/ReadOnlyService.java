package com.zb.springboot.demo.service.transaction;

import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.mapper.UserMapper;
import com.zb.springboot.demo.service.NotReadOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bo6.zhang
 * @date 2021/2/7
 */
@Service
public class ReadOnlyService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotReadOnlyService notReadOnlyService;

    @Transactional(readOnly = true)
    public User readOnly() {
        User user = userMapper.selectById(1);
        // 在声明readOny=true后，主要实际的操作没有写操作，就不会报错
//        userMapper.updateById(user);
//        notReadOnlyService.notReadOnly();
        return user;
    }

}