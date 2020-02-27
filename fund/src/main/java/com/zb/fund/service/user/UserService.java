package com.zb.fund.service.user;

import com.zb.fund.domain.User;

import java.util.List;

/**
 * @author zhangbo
 */
public interface UserService {
    
    int batchInsert(List<User> users);
    
    int batchUpdate(List<User> users);

    User list();
}
