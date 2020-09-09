package com.zb.springboot.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.springboot.demo.entity.User;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zhangbo
 * @since 2019-07-23
 */
public interface UserMapper extends BaseMapper<User> {

    User selectByUserId(Long id);

}
