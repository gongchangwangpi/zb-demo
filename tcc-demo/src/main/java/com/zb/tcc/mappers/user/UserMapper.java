package com.zb.tcc.mappers.user;

import com.zb.tcc.domain.user.User;

/**
 * @author zhangbo
 */
public interface UserMapper {
    
    int insert(User user);
    
    int update(User user);
    
    User select(Long id);
    
    User select(String username);
    
}
