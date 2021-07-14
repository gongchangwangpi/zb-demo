package com.zb.springboot.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.springboot.demo.entity.User;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zhangbo
 * @since 2019-07-23
 */
public interface UserMapper extends BaseMapper<User> {

    int insert(User user);

    User selectByUserId(Long id);

    User selectByUsername(String username);

    int batchInsert(List<User> userList);
}
