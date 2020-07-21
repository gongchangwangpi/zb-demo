package com.zb.springboot.demo.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zb.springboot.demo.entity.User;

/**
 * @author zhangbo
 * @date 2020/4/23
 */
public interface UserService extends IService<User> {

    Long insert(User user);

}
