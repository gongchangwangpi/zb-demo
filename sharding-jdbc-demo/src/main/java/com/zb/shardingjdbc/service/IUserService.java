package com.zb.shardingjdbc.service;

import com.zb.shardingjdbc.domain.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangbo
 * @since 2020-02-08
 */
public interface IUserService {

    User get(Long id);

    void save(User user);

}
