package com.zb.shardingjdbc.mapper;

import com.zb.shardingjdbc.domain.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangbo
 * @since 2020-02-08
 */
public interface UserMapper {

    User selectById(Long id);

    int insert(User user);

}
